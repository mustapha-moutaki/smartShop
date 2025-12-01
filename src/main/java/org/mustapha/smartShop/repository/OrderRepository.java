package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.enums.OrderStatus;
import org.mustapha.smartShop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);
    Long countByClientIdAndStatus(Long id, OrderStatus orderStatus);
    Long sumTotalByClientIdAndStatus(Long id, OrderStatus orderStatus);
}
