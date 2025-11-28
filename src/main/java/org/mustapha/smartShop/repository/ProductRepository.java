package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByDeletedFalse(); // list of deleted products
}
