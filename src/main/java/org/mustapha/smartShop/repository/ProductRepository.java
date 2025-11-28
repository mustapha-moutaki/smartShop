package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByDeletedFalse(); // list of deleted products
    Optional<Product> findByNameIgnoreCase(String name);
    Optional<Product>findById(Long id);
}
