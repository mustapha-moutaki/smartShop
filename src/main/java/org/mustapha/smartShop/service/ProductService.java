package org.mustapha.smartShop.service;

import org.mustapha.smartShop.dto.request.ProductDtoRequest;
import org.mustapha.smartShop.dto.response.ProductDtoResponse;
import org.springframework.data.domain.Page;


public interface ProductService {
    ProductDtoResponse createProduct(ProductDtoRequest productDtoRequest);

    ProductDtoResponse updateProduct(Long id, ProductDtoRequest productDtoRequest);

    ProductDtoResponse getProductById(Long id);

    Page<ProductDtoResponse> getAllProducts(int page, int size);

    void softDelete(Long id);

}
