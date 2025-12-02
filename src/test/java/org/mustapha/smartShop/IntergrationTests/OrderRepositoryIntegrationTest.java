package org.mustapha.smartShop.IntergrationTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mustapha.smartShop.enums.LoyaltyLevel;
import org.mustapha.smartShop.enums.OrderStatus;
import org.mustapha.smartShop.enums.UserRole;
import org.mustapha.smartShop.model.*;
import org.mustapha.smartShop.repository.ClientRepository;
import org.mustapha.smartShop.repository.OrderRepository;
import org.mustapha.smartShop.repository.ProductRepository;
import org.mustapha.smartShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrderRepositoryIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveOrderWithItems() {
        User user = new User();
        user.setUsername("mustapha123");
        user.setPassword("password");
        user.setRole(UserRole.CLIENT);
        userRepository.save(user);

        Client client = new Client();
        client.setName("Mustapha");
        client.setEmail("mustapha@example.com");
        client.setLoyaltyLevel(LoyaltyLevel.BRONZE);
        client.setUser(user); // <- important!
        clientRepository.save(client);


        // Create products
        Product product1 = new Product();
        product1.setName("Keyboard");
        product1.setUnitPrice(50.0);
        product1.setStock(100);
        product1.setDeleted(false);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setUnitPrice(30.0);
        product2.setStock(200);
        product2.setDeleted(false);
        productRepository.save(product2);

        // Create order items
        OrderItem item1 = new OrderItem();
        item1.setProduct(product1);
        item1.setQuantity(2);
        item1.setUnitPrice(product1.getUnitPrice());

        OrderItem item2 = new OrderItem();
        item2.setProduct(product2);
        item2.setQuantity(3);
        item2.setUnitPrice(product2.getUnitPrice());

        // Create order
        Order order = new Order();
        order.setClient(client);
        order.setItems(List.of(item1, item2));
        order.setStatus(OrderStatus.PENDING);
        order.setSubTotal(50*2 + 30*3);
        order.setDiscount(0.0);
        order.setVat(order.getSubTotal()*0.2); // example 20% VAT
        order.setTotal(order.getSubTotal() + order.getVat());
        order.setRemainingAmount(order.getTotal());

        // Set back-reference
        item1.setOrder(order);
        item2.setOrder(order);

        Order savedOrder = orderRepository.save(order);

        // Assertions
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getClient().getName()).isEqualTo("Mustapha");
        assertThat(savedOrder.getItems()).hasSize(2);
        assertThat(savedOrder.getTotal()).isEqualTo(50*2 + 30*3 + (50*2 + 30*3)*0.2);
    }

    @Test
    void testFindOrdersByClientId() {
        // Create and save a user
        User user = new User();
        user.setUsername("mustapha123");
        user.setPassword("password");
        user.setRole(UserRole.CLIENT);
        userRepository.save(user);

        // Create and save a client
        Client client = new Client();
        client.setName("Mustapha");
        client.setEmail("mustapha@example.com");
        client.setLoyaltyLevel(LoyaltyLevel.BRONZE);
        client.setUser(user);
        clientRepository.save(client);

        // Create and save an order for this client
        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(100.0);
        order.setRemainingAmount(100.0);
        orderRepository.save(order);

        // Now query
        List<Order> orders = orderRepository.findByClient_Id(client.getId());

        // Assertions
        assertThat(orders).isNotEmpty();
        assertThat(orders.get(0).getClient().getId()).isEqualTo(client.getId());
    }

}
