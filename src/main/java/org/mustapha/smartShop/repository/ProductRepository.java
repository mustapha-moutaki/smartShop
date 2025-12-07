package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
   Page<Product> findByDeletedFalse(Pageable pageable); // instead of return all products and then filter we can return just indeleted products
    Optional<Product> findByNameIgnoreCase(String name);
    Optional<Product>findById(Long id);

}
