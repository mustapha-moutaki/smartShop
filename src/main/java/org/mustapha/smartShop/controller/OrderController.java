package org.mustapha.smartShop.controller;

import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.OrderDtoRequest;
import org.mustapha.smartShop.dto.response.OrderDtoResponse;
import org.mustapha.smartShop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 1️⃣ Create Order
    @PostMapping
    public ResponseEntity<OrderDtoResponse> createOrder(@Valid @RequestBody OrderDtoRequest orderDtoRequest) {
        OrderDtoResponse createdOrder = orderService.createOrder(orderDtoRequest);
        return ResponseEntity.ok(createdOrder);
    }

    // 2️⃣ Update Order
    @PutMapping("/{id}")
    public ResponseEntity<OrderDtoResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDtoRequest orderDtoRequest) {
        OrderDtoResponse updatedOrder = orderService.updateOrder(id, orderDtoRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    // 3️⃣ Get Order by Id
    @GetMapping("/{id}")
    public ResponseEntity<OrderDtoResponse> getOrderById(@PathVariable Long id) {
        OrderDtoResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // 4️⃣ Get All Orders
    @GetMapping
    public ResponseEntity<List<OrderDtoResponse>> getAllOrders() {
        List<OrderDtoResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // 5️⃣ Cancel Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    // 6️⃣ Confirm Order
    @PostMapping("/{id}/confirm")
    public ResponseEntity<OrderDtoResponse> confirmOrder(@PathVariable Long id) {
        OrderDtoResponse confirmedOrder = orderService.confirmOrder(id);
        return ResponseEntity.ok(confirmedOrder);
    }
}
