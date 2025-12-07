package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.dto.response.ClientOrderDtoResponse;
import org.mustapha.smartShop.enums.OrderStatus;
import org.mustapha.smartShop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);
    Long countByClientIdAndStatus(Long id, OrderStatus orderStatus);

    @Query("SELECT SUM(o.total) FROM Order o WHERE o.client.id = :id AND o.status = :orderStatus")
    Long sumTotalByClientIdAndStatus(Long id, OrderStatus orderStatus);

    List<Order> findByClient_Id(Long id);
}
