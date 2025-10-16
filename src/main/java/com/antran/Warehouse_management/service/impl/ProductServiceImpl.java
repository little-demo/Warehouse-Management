package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Product.ProductRequest;
import com.antran.Warehouse_management.dto.request.Product.UnitConversionRequest;
import com.antran.Warehouse_management.dto.response.ProductResponse;
import com.antran.Warehouse_management.entity.Category;
import com.antran.Warehouse_management.entity.Product;
import com.antran.Warehouse_management.entity.UnitConversion;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.ProductMapper;
import com.antran.Warehouse_management.repository.CategoryRepository;
import com.antran.Warehouse_management.repository.GoodsReceiptDetailRepository;
import com.antran.Warehouse_management.repository.ProductRepository;
import com.antran.Warehouse_management.repository.UnitConversionRepository;
import com.antran.Warehouse_management.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    UnitConversionRepository unitConversionRepository;
    GoodsReceiptDetailRepository goodsReceiptDetailRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        try {
            if (productRepository.existsBySku(request.getSku())) {
                throw new AppException(ErrorCode.PRODUCT_SKU_ALREADY_EXISTS);
            }

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

            // 1. Tạo product
            Product product = ProductMapper.toEntity(request, category);
            Product savedProduct = productRepository.save(product);

            // 2. Thêm baseUnit vào conversions (ratio = 1)
            UnitConversion baseConversion = UnitConversion.builder()
                    .product(savedProduct)
                    .unitName(request.getBaseUnit())
                    .ratioToBase(BigDecimal.ONE)
                    .build();
            unitConversionRepository.save(baseConversion);
            savedProduct.getUnitConversions().add(baseConversion);

            // 3. Thêm conversions khác từ request
            if (request.getConversions() != null) {
                for (UnitConversionRequest conv : request.getConversions()) {
                    if (conv.getUnitName().equalsIgnoreCase(request.getBaseUnit())) {
                        throw new AppException(ErrorCode.UNIT_ALREADY_EXISTS_AS_BASE);
                    }

                    UnitConversion conversion = UnitConversion.builder()
                            .product(savedProduct)
                            .unitName(conv.getUnitName())
                            .ratioToBase(conv.getRatioToBase())
                            .build();
                    unitConversionRepository.save(conversion);
                    savedProduct.getUnitConversions().add(conversion);
                }
            }

            return ProductMapper.toResponse(savedProduct);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public ProductResponse getProductById(int id) {
        return ProductMapper.toResponse(findProductById(id));
    }

//    @Override
//    @Transactional
//    public ProductResponse updateProduct(int id, ProductRequest request) {
//        Product product = findProductById(id);
//
//        if (!product.getSku().equals(request.getSku()) &&
//                productRepository.existsBySku(request.getSku())) {
//            throw new AppException(ErrorCode.PRODUCT_SKU_ALREADY_EXISTS);
//        }
//
//        Category category = categoryRepository.findById(request.getCategoryId())
//                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
//
//        product.setSku(request.getSku());
//        product.setName(request.getName());
//        product.setMinStockLevel(request.getMinStockLevel());
//        product.setCategory(category);
//        product.setBaseUnit(request.getBaseUnit());
//
//        // update baseUnit trong conversions
//        UnitConversion baseConv = unitConversionRepository.findByProductIdAndRatioToBase(id, BigDecimal.ONE)
//                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));
//
//        if (!baseConv.getUnitName().equalsIgnoreCase(request.getBaseUnit())) {
//            baseConv.setUnitName(request.getBaseUnit()); // đổi tên baseUnit
//            unitConversionRepository.save(baseConv);
//        }
//
//        // update các conversions khác
//        if (request.getConversions() != null) {
//            // Xoá conversions cũ (trừ baseUnit)
//            unitConversionRepository.deleteByProductIdAndRatioToBaseNot(id, BigDecimal.ONE);
//
//            // Insert lại conversions từ request
//            for (UnitConversionRequest conv : request.getConversions()) {
//                if (conv.getUnitName().equalsIgnoreCase(request.getBaseUnit())) {
//                    throw new AppException(ErrorCode.UNIT_ALREADY_EXISTS_AS_BASE);
//                }
//
//                UnitConversion conversion = UnitConversion.builder()
//                        .product(product)
//                        .unitName(conv.getUnitName())
//                        .ratioToBase(conv.getRatioToBase())
//                        .build();
//                unitConversionRepository.save(conversion);
//            }
//        }
//
//        return ProductMapper.toResponse(productRepository.save(product));
//    }
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

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setMinStockLevel(request.getMinStockLevel());
        product.setCategory(category);
        product.setBaseUnit(request.getBaseUnit());

        // Update base unit
        UnitConversion baseConv = unitConversionRepository.findByProductIdAndRatioToBase(id, BigDecimal.ONE)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));

        if (!baseConv.getUnitName().equalsIgnoreCase(request.getBaseUnit())) {
            baseConv.setUnitName(request.getBaseUnit());
            unitConversionRepository.save(baseConv);
        }

        // Đồng bộ các conversion còn lại
        if (request.getConversions() != null) {
            List<UnitConversion> existingConversions =
                    unitConversionRepository.findByProductIdAndRatioToBaseNot(id, BigDecimal.ONE);

            // Map hiện tại để tra nhanh theo tên đơn vị
            Map<String, UnitConversion> existingMap = existingConversions.stream()
                    .collect(Collectors.toMap(UnitConversion::getUnitName, uc -> uc, (a, b) -> a));

            Set<String> incomingNames = new HashSet<>();

            for (UnitConversionRequest convReq : request.getConversions()) {
                incomingNames.add(convReq.getUnitName());

                // Đơn vị trùng với baseUnit => lỗi
                if (convReq.getUnitName().equalsIgnoreCase(request.getBaseUnit())) {
                    throw new AppException(ErrorCode.UNIT_ALREADY_EXISTS_AS_BASE);
                }

                UnitConversion existing = existingMap.get(convReq.getUnitName());
                if (existing != null) {
                    // Cập nhật nếu khác
                    if (existing.getRatioToBase().compareTo(convReq.getRatioToBase()) != 0) {
                        existing.setRatioToBase(convReq.getRatioToBase());
                        unitConversionRepository.save(existing);
                    }
                } else {
                    // Thêm mới
                    UnitConversion newConv = UnitConversion.builder()
                            .product(product)
                            .unitName(convReq.getUnitName())
                            .ratioToBase(convReq.getRatioToBase())
                            .build();
                    unitConversionRepository.save(newConv);
                }
            }

            // Xóa các conversion không còn trong request (nếu chưa từng được dùng)
            for (UnitConversion oldConv : existingConversions) {
                if (!incomingNames.contains(oldConv.getUnitName())) {
                    boolean inUse = goodsReceiptDetailRepository.existsByUnitConversionId(oldConv.getId());
                    if (!inUse) {
                        unitConversionRepository.delete(oldConv);
                    }
                }
            }
        }

        return ProductMapper.toResponse(productRepository.save(product));
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
