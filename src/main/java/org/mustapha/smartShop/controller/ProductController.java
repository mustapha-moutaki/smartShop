package org.mustapha.smartShop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.ProductDtoRequest;
import org.mustapha.smartShop.dto.response.ProductDtoResponse;
import org.mustapha.smartShop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/products")
@Tag(name = "Products", description = "Product management endpoints")
public class ProductController {

    private final ProductService productService;

    // create product
    @PostMapping
    @Operation(summary = "Create product", description = "Create a new product")
    public ResponseEntity<ProductDtoResponse> createProduct(@Valid @RequestBody ProductDtoRequest productDtoRequest) {
        ProductDtoResponse createdProduct = productService.createProduct(productDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // update product
    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product by ID")
    public ResponseEntity<ProductDtoResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDtoRequest productDtoRequest) {
        ProductDtoResponse productUpdated = productService.updateProduct(id, productDtoRequest);
        return ResponseEntity.ok(productUpdated);
    }

    // get by id
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a product using its ID")
    public ResponseEntity<ProductDtoResponse> getById(@PathVariable Long id) {
        ProductDtoResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // get all products
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve paginated list of all products with optional filter and sorting")
    public ResponseEntity<Page<ProductDtoResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,        // fitler by name
            @RequestParam(defaultValue = "id") String sortBy,   // sort by any filed
            @RequestParam(defaultValue = "asc") String sortDir  // sort direct asc desc
    ) {
        Page<ProductDtoResponse> productList = productService.getAllProducts(page, size, name, sortBy, sortDir);
        return ResponseEntity.ok(productList);
    }

    // delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Soft delete a product by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
