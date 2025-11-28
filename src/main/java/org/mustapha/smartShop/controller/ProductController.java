package org.mustapha.smartShop.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.mustapha.smartShop.dto.request.ProductDtoRequest;
import org.mustapha.smartShop.dto.response.ProductDtoResponse;
import org.mustapha.smartShop.model.Product;
import org.mustapha.smartShop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/products")
public class ProductController{

    private final ProductService productService;

    // create product
    @PostMapping
    public ResponseEntity<ProductDtoResponse>createProduct(@Valid @RequestBody ProductDtoRequest productDtoRequest){
        // 1- save the product
        ProductDtoResponse createdProduct = productService.createProduct(productDtoRequest);
        // 2- retunr the response
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // update product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDtoResponse>updateProduct(@PathVariable Long id,@Valid  @RequestBody ProductDtoRequest productDtoRequest){
        // update product
        ProductDtoResponse productUpdated = productService.updateProduct(id, productDtoRequest);
        // retunr the response
        return ResponseEntity.ok(productUpdated);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductDtoResponse>getById(@PathVariable Long id){
        ProductDtoResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // get all products
    @GetMapping
    public ResponseEntity<Page<ProductDtoResponse>>getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<ProductDtoResponse>productList = productService.getAllProducts(page, size);
        return ResponseEntity.ok(productList);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        productService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
