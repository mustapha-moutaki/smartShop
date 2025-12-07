package org.mustapha.smartShop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mustapha.smartShop.dto.request.ProductDtoRequest;
import org.mustapha.smartShop.dto.response.ProductDtoResponse;
import org.mustapha.smartShop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDtoRequest productDtoRequest;
    private ProductDtoResponse productDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize request
        productDtoRequest = new ProductDtoRequest();
        productDtoRequest.setName("Laptop");
        productDtoRequest.setUnitPrice(1200.0);
        productDtoRequest.setStock(10);

        // Initialize response
        productDtoResponse = new ProductDtoResponse();
        productDtoResponse.setId(1L);
        productDtoResponse.setName("Laptop");
        productDtoResponse.setUnitPrice(1200.0);
        productDtoResponse.setStock(10);
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(productDtoRequest)).thenReturn(productDtoResponse);

        ResponseEntity<ProductDtoResponse> response = productController.createProduct(productDtoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getName());
        verify(productService, times(1)).createProduct(productDtoRequest);
    }

    @Test
    void testGetById() {
        when(productService.getProductById(1L)).thenReturn(productDtoResponse);

        ResponseEntity<ProductDtoResponse> response = productController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(1L, productDtoRequest)).thenReturn(productDtoResponse);

        ResponseEntity<ProductDtoResponse> response = productController.updateProduct(1L, productDtoRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(productService, times(1)).updateProduct(1L, productDtoRequest);
    }

    @Test
    void testGetAllProducts() {
        List<ProductDtoResponse> productList = new ArrayList<>();
        productList.add(productDtoResponse);
        Page<ProductDtoResponse> productPage = new PageImpl<>(productList);


        when(productService.getAllProducts(0, 10, null, null, null)).thenReturn(productPage);

        ResponseEntity<Page<ProductDtoResponse>> response = productController.getAll(0, 10, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());


        verify(productService, times(1)).getAllProducts(0, 10, null, null, null);
    }


    @Test
    void testDeleteProduct() {
        doNothing().when(productService).softDelete(1L);

        ResponseEntity<Void> response = productController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).softDelete(1L);
    }
}
