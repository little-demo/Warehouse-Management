package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.Product.ProductRequest;
import com.antran.Warehouse_management.dto.response.ProductResponse;
import com.antran.Warehouse_management.entity.Category;
import com.antran.Warehouse_management.entity.Product;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.ProductMapper;
import com.antran.Warehouse_management.repository.CategoryRepository;
import com.antran.Warehouse_management.repository.ProductRepository;
import com.antran.Warehouse_management.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new AppException(ErrorCode.PRODUCT_SKU_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        Product product = ProductMapper.toEntity(request, category);

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse getProductById(int id) {
        return ProductMapper.toResponse(findProductById(id));
    }

    @Override
    public ProductResponse updateProduct(int id, ProductRequest request) {
        Product product = findProductById(id);

        if (!product.getSku().equals(request.getSku()) && productRepository.existsBySku(request.getSku())) {
            throw new AppException(ErrorCode.PRODUCT_SKU_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setUnit(request.getUnit());
        product.setMinStockLevel(request.getMinStockLevel());
        product.setCategory(category);

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
