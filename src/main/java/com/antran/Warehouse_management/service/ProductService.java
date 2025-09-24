package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.Product.ProductRequest;
import com.antran.Warehouse_management.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(int id);
    ProductResponse updateProduct(int id, ProductRequest request);
    List<ProductResponse> getAllProducts();
    void changeProductStatus(int id);
    void deleteProduct(int id);
}
