package org.mustapha.smartShop.IntergrationTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mustapha.smartShop.model.Product;
import org.mustapha.smartShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindById() {
        // Create a product
        Product product = new Product();
        product.setName("Laptop");
        product.setUnitPrice(1500.0);
        product.setStock(10);
        product.setDeleted(false);

        // Save product
        Product saved = productRepository.save(product);

        // Retrieve product by ID
        Optional<Product> found = productRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Laptop");
        assertThat(found.get().getUnitPrice()).isEqualTo(1500.0);
    }

    @Test
    void testFindByNameIgnoreCase() {
        Product product = new Product();
        product.setName("Mouse");
        product.setUnitPrice(25.0);
        product.setStock(50);
        product.setDeleted(false);
        productRepository.save(product);

        Optional<Product> found = productRepository.findByNameIgnoreCase("mouse");

        assertThat(found).isPresent();
        assertThat(found.get().getStock()).isEqualTo(50);
    }
}
