package org.mustapha.smartShop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mustapha.smartShop.dto.request.ProductDtoRequest;
import org.mustapha.smartShop.dto.response.ProductDtoResponse;
import org.mustapha.smartShop.exception.BusinessRuleException;
import org.mustapha.smartShop.exception.ResourceNotFoundException;
import org.mustapha.smartShop.mapper.ProductMapper;
import org.mustapha.smartShop.model.Product;
import org.mustapha.smartShop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDtoRequest productDtoRequest;
    private Product product;
    private ProductDtoResponse productDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productDtoRequest = new ProductDtoRequest();
        productDtoRequest.setName("Laptop");
        productDtoRequest.setStock(10);
        productDtoRequest.setUnitPrice(1000.0);

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setStock(10);
        product.setUnitPrice(1000.0);
        product.setDeleted(false);

        productDtoResponse = new ProductDtoResponse();
        productDtoResponse.setId(1L);
        productDtoResponse.setName("Laptop");
        productDtoResponse.setStock(10);
        productDtoResponse.setUnitPrice(1000.0);
    }

    @Test
    void testCreateProductSuccess() {
        when(productRepository.findByNameIgnoreCase("Laptop")).thenReturn(Optional.empty());
        when(productMapper.toEntity(productDtoRequest)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDtoResponse);

        ProductDtoResponse response = productService.createProduct(productDtoRequest);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testCreateProductDuplicateThrowsException() {
        when(productRepository.findByNameIgnoreCase("Laptop")).thenReturn(Optional.of(product));

        assertThrows(BusinessRuleException.class, () -> productService.createProduct(productDtoRequest));
    }

    @Test
    void testUpdateProductSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDtoResponse);

        ProductDtoResponse response = productService.updateProduct(1L, productDtoRequest);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProductNotFoundThrowsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(1L, productDtoRequest));
    }

    @Test
    void testGetProductByIdSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDtoResponse);

        ProductDtoResponse response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());
    }

    @Test
    void testGetProductByIdNotFoundThrowsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Page<Product> page = new PageImpl<>(productList);

        when(productRepository.findByDeletedFalse(any(Pageable.class))).thenReturn(page);
        when(productMapper.toDto(product)).thenReturn(productDtoResponse);

        Page<ProductDtoResponse> response = productService.getAllProducts(0, 10);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals("Laptop", response.getContent().get(0).getName());
    }

    @Test
    void testSoftDeleteProductSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.softDelete(1L);

        assertTrue(product.isDeleted());
    }

    @Test
    void testSoftDeleteProductNotFoundThrowsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.softDelete(1L));
    }
}
