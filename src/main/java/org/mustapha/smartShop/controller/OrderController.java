package org.mustapha.smartShop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.OrderDtoRequest;
import org.mustapha.smartShop.dto.response.ClientOrderDtoResponse;
import org.mustapha.smartShop.dto.response.OrderDtoResponse;
import org.mustapha.smartShop.dto.response.OrderItemDtoResponse;
import org.mustapha.smartShop.model.Order;
import org.mustapha.smartShop.model.OrderItem;
import org.mustapha.smartShop.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "APIs for managing orders")
public class OrderController {

    private final OrderService orderService;

    // Create Order
    @PostMapping
    @Operation(summary = "Create new order", description = "Creates an order with items and calculates totals")
    public ResponseEntity<OrderDtoResponse> createOrder(@Valid @RequestBody OrderDtoRequest orderDtoRequest) {
        OrderDtoResponse createdOrder = orderService.createOrder(orderDtoRequest);
        return ResponseEntity.ok(createdOrder);
    }

    // Update Order
    @PutMapping("/{id}")
    @Operation(summary = "Update order", description = "Updates an existing order by its ID")
    public ResponseEntity<OrderDtoResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDtoRequest orderDtoRequest) {
        OrderDtoResponse updatedOrder = orderService.updateOrder(id, orderDtoRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    // Get Order by Id
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Returns a single order using its ID")
    public ResponseEntity<OrderDtoResponse> getOrderById(@PathVariable Long id) {
        OrderDtoResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Get All Orders
    @GetMapping
    @Operation(summary = "Get all orders", description = "Returns a list of all orders in the system")
    public ResponseEntity<List<OrderDtoResponse>> getAllOrders() {
        List<OrderDtoResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Cancel Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    // Confirm Order
    @PostMapping("/{id}/confirm")
    public ResponseEntity<OrderDtoResponse> confirmOrder(@PathVariable Long id) {
        OrderDtoResponse confirmedOrder = orderService.confirmOrder(id);
        return ResponseEntity.ok(confirmedOrder);
    }


//    @GetMapping("/my-history")
//    public ResponseEntity<List<ClientOrderDtoResponse>> getMyHistory(HttpSession session) {
//
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return ResponseEntity.status(401).body(null);
//        }
//
//        List<Order> orders = orderService.findOrdersByClientId(userId);
//
//        List<ClientOrderDtoResponse> responseList = orders.stream().map(order -> {
//            ClientOrderDtoResponse dto = new ClientOrderDtoResponse();
//            dto.setDate(order.getDate());
//            dto.setSubTotal(order.getSubTotal());
//            dto.setDiscount(order.getDiscount());
//            dto.setVat(order.getVat());
//            dto.setTotal(order.getTotal());
//            dto.setItems(mapItems(order.getItems()));
//            return dto;
//        }).toList();
//
//        return ResponseEntity.ok(responseList);
//    }
//    private List<OrderItemDtoResponse> mapItems(List<OrderItem> items) {
//        if (items == null) return new ArrayList<>();
//
//        return items.stream().map(item -> {
//            OrderItemDtoResponse dto = new OrderItemDtoResponse();
//            BeanUtils.copyProperties(item, dto);
//            return dto;
//        }).toList();
//    }
@GetMapping("/my-history/{clientId}")
public ResponseEntity<List<ClientOrderDtoResponse>> getMyHistory(@PathVariable Long clientId) {
    List<Order> orders = orderService.findOrdersByClientId(clientId);

    List<ClientOrderDtoResponse> responseList = orders.stream().map(order -> {
        ClientOrderDtoResponse dto = new ClientOrderDtoResponse();
        dto.setId(order.getId());
        dto.setDate(order.getDate());
        dto.setSubTotal(order.getSubTotal());
        dto.setDiscount(order.getDiscount());
        dto.setVat(order.getVat());
        dto.setTotal(order.getTotal());
        dto.setItemIds(order.getItems().stream().map(OrderItem::getId).toList());
        return dto;
    }).toList();

    return ResponseEntity.ok(responseList);
}

}
