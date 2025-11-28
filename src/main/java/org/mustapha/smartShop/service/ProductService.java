package org.mustapha.smartShop.service;

import org.mustapha.smartShop.dto.request.ProductDtoRequest;
import org.mustapha.smartShop.dto.response.ProductDtoResponse;
import org.mustapha.smartShop.model.Product;

import java.util.List;

public interface ProductService {
    ProductDtoResponse createProduct(ProductDtoRequest productDtoRequest);

    ProductDtoResponse updateProduct(Long id, ProductDtoRequest productDtoRequest);

    ProductDtoResponse getProductById(Long id);

    List<ProductDtoResponse> getAllProducts();

    void softDelete(Long id);

}
