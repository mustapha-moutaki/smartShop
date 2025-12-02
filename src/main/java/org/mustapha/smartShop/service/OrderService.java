package org.mustapha.smartShop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.OrderDtoRequest;
import org.mustapha.smartShop.dto.response.ClientOrderDtoResponse;
import org.mustapha.smartShop.dto.response.OrderDtoResponse;
import org.mustapha.smartShop.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface OrderService {

    OrderDtoResponse createOrder(OrderDtoRequest orderDtoRequest);


    OrderDtoResponse updateOrder(Long id, OrderDtoRequest orderDtoRequest);


    OrderDtoResponse getOrderById(Long id);


    List<OrderDtoResponse> getAllOrders();


    void cancelOrder(Long id);


    OrderDtoResponse confirmOrder(Long id);

    List<Order> findOrdersByClientId(Long id);
}
