package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Product.ProductRequest;
import com.antran.Warehouse_management.dto.request.Product.UnitConversionRequest;
import com.antran.Warehouse_management.dto.response.ProductResponse;
import com.antran.Warehouse_management.entity.Category;
import com.antran.Warehouse_management.entity.Product;
import com.antran.Warehouse_management.entity.ProductUnitConversion;
import com.antran.Warehouse_management.entity.Unit;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.ProductMapper;
import com.antran.Warehouse_management.repository.CategoryRepository;
import com.antran.Warehouse_management.repository.ProductRepository;
import com.antran.Warehouse_management.repository.ProductUnitConversionRepository;
import com.antran.Warehouse_management.repository.UnitRepository;
import com.antran.Warehouse_management.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    UnitRepository unitRepository;
    ProductUnitConversionRepository conversionRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        try {
            if (productRepository.existsBySku(request.getSku())) {
                throw new AppException(ErrorCode.PRODUCT_SKU_ALREADY_EXISTS);
            }

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

            Unit baseUnit = unitRepository.findById(request.getBaseUnitId())
                    .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));

            // 1. Tạo và lưu product trước
            Product product = ProductMapper.toEntity(request, category);
            Product savedProduct = productRepository.save(product);

            // 2. Tạo conversions
            Set<ProductUnitConversion> allConversions = createConversions(savedProduct, baseUnit, request.getConversions());

            // 3. Lưu tất cả conversions
            Set<ProductUnitConversion> savedConversions = new HashSet<>(conversionRepository.saveAll(allConversions));

            // 4. Set conversions cho product
            savedProduct.setConversions(savedConversions);

            return ProductMapper.toResponse(savedProduct);

        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private Set<ProductUnitConversion> createConversions(Product product, Unit baseUnit, List<UnitConversionRequest> conversionRequests) {
        Set<ProductUnitConversion> conversions = new HashSet<>();

        // Luôn thêm base conversion (ratio = 1)
        ProductUnitConversion baseConversion = ProductUnitConversion.builder()
                .product(product)
                .unit(baseUnit)
                .ratioToBase(BigDecimal.ONE)
                .build();
        conversions.add(baseConversion);

        // Thêm các conversion khác từ request
        if (conversionRequests != null) {
            for (UnitConversionRequest convRequest : conversionRequests) {
                Unit unit = unitRepository.findById(convRequest.getUnitId())
                        .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));

                // Validate unit không trùng với base unit
                if (unit.getId() == (baseUnit.getId())) {
                    throw new AppException(ErrorCode.UNIT_ALREADY_EXISTS_AS_BASE);
                }

                // Validate ratio hợp lệ
                if (convRequest.getRatioToBase().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new AppException(ErrorCode.INVALID_CONVERSION_RATIO);
                }

                ProductUnitConversion conversion = ProductUnitConversion.builder()
                        .product(product)
                        .unit(unit)
                        .ratioToBase(convRequest.getRatioToBase())
                        .build();
                conversions.add(conversion);
            }
        }

        return conversions;
    }

    @Override
    public ProductResponse getProductById(int id) {
        return ProductMapper.toResponse(findProductById(id));
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(int id, ProductRequest request) {
        Product product = findProductById(id);

        if (!product.getSku().equals(request.getSku()) &&
                productRepository.existsBySku(request.getSku())) {
            throw new AppException(ErrorCode.PRODUCT_SKU_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        Unit newBaseUnit = unitRepository.findById(request.getBaseUnitId())
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setMinStockLevel(request.getMinStockLevel());
        product.setCategory(category);

        // ---- Xử lý baseUnit trong conversions ----
        updateBaseUnitConversion(product, newBaseUnit);

        return ProductMapper.toResponse(productRepository.save(product));
    }

    private void updateBaseUnitConversion(Product product, Unit newBaseUnit) {
        // Tìm base conversion hiện tại (ratio = 1)
        ProductUnitConversion currentBaseConv = product.getConversions().stream()
                .filter(conv -> conv.getRatioToBase().compareTo(BigDecimal.ONE) == 0)
                .findFirst()
                .orElse(null);

        if (currentBaseConv == null) {
            // Trường hợp 1: Chưa có base conversion → thêm mới
            ProductUnitConversion baseConversion = ProductUnitConversion.builder()
                    .product(product)
                    .unit(newBaseUnit)
                    .ratioToBase(BigDecimal.ONE)
                    .build();
            product.getConversions().add(baseConversion);

        } else if (currentBaseConv.getUnit().getId() != newBaseUnit.getId()) {
            // Trường hợp 2: Đổi base unit

            Unit oldBaseUnit = currentBaseConv.getUnit();

            if (isBaseUnitUsedInOtherConversions(product, oldBaseUnit)) {
                throw new AppException(ErrorCode.BASE_UNIT_CANNOT_BE_CHANGED);
            }

            // Update base unit
            currentBaseConv.setUnit(newBaseUnit);
        }
        // Trường hợp 3: Base unit không đổi → không làm gì
    }

    private boolean isBaseUnitUsedInOtherConversions(Product product, Unit baseUnit) {
        return product.getConversions().stream()
                .filter(conv -> conv.getRatioToBase().compareTo(BigDecimal.ONE) != 0) // Loại trừ base conversion
                .anyMatch(conv -> conv.getUnit().equals(baseUnit));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public void changeProductStatus(int id) {
        Product product = findProductById(id);
        product.setActive(!product.isActive());
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    Product findProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
    }
}
