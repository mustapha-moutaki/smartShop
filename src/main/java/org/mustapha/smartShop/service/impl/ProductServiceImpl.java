package org.mustapha.smartShop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.ProductDtoRequest;
import org.mustapha.smartShop.dto.response.ProductDtoResponse;
import org.mustapha.smartShop.exception.BusinessRuleException;
import org.mustapha.smartShop.exception.ResourceNotFoundException;
import org.mustapha.smartShop.mapper.ProductMapper;
import org.mustapha.smartShop.model.Product;
import org.mustapha.smartShop.repository.ProductRepository;
import org.mustapha.smartShop.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ProductDtoResponse createProduct(ProductDtoRequest productDtoRequest) {
        // check if the product is already eixst
        productRepository.findByNameIgnoreCase(productDtoRequest.getName()).ifPresent(
                p-> {
                    throw new BusinessRuleException("the product with name : " + productDtoRequest.getName() + "is already exist");
                }
        );

        // if not -- create new product
        // 2. Convert the request DTO to an Entity
        Product product = productMapper.toEntity(productDtoRequest);

        // 3. Set default values (Business Logic)
        product.setDeleted(false);

        // 4. Save the product to the database
        Product savedProduct = productRepository.save(product);

        // return the response dto
        return productMapper.toDto(savedProduct);

    }

    @Override
    public ProductDtoResponse updateProduct(Long id, ProductDtoRequest productDtoRequest) {
        // check if the product is already exist
        Product product  = productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Product not find with the id: " + id )
        );
        // udpate the data
        product.setName(productDtoRequest.getName());
        product.setStock(productDtoRequest.getStock());
        product.setUnitPrice(productDtoRequest.getUnitPrice());
        // update the product
        Product updatedProduct = productRepository.save(product);
        // retunr the ersponse as prodct dto repsonse
        return productMapper.toDto(updatedProduct);
    }

    @Override
    public ProductDtoResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("product not found with id: " + id)
        );
        return productMapper.toDto(product);
    }

//    @Override
//    public Page<ProductDtoResponse> getAllProducts(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Product> productsList = productRepository.findByDeletedFalse(pageable);
//        return productsList.map(productMapper::toDto);
//    }
@Override
public Page<ProductDtoResponse> getAllProducts(int page, int size, String name, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Product> productsList;

    if (name != null && !name.isEmpty()) {
        // filter by name directly in db
        productsList = productRepository.findByDeletedFalseAndNameContainingIgnoreCase(name, pageable);
    } else {
        productsList = productRepository.findByDeletedFalse(pageable);
    }

    return productsList.map(productMapper::toDto);
}


    @Override
    public void softDelete(Long id) {
        // 1- check exist product
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("product not find with id: "+ id)
        );

        // 2- if exist soft delete it
           product.setDeleted(true);

        // 3- update it but we use transactional so it's update automaticaally
//        productRepository.save(product);

    }
}
