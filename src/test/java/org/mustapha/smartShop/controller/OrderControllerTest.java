package org.mustapha.smartShop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mustapha.smartShop.dto.request.OrderDtoRequest;
import org.mustapha.smartShop.dto.response.OrderDtoResponse;
import org.mustapha.smartShop.service.OrderService;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private OrderController orderController;

    private OrderDtoRequest orderDtoRequest;
    private OrderDtoResponse orderDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize request
        orderDtoRequest = new OrderDtoRequest();
        orderDtoRequest.setClientId(1L);
        orderDtoRequest.setPromoCodeId(null);
        orderDtoRequest.setItems(new ArrayList<>());

        // Initialize response
        orderDtoResponse = new OrderDtoResponse();
        orderDtoResponse.setId(1L);
        orderDtoResponse.setSubTotal(1000.0);
        orderDtoResponse.setDiscount(50.0);
        orderDtoResponse.setVat(190.0);
        orderDtoResponse.setTotal(1140.0);
    }

    @Test
    void testCreateOrder() {
        when(orderService.createOrder(any(OrderDtoRequest.class))).thenReturn(orderDtoResponse);

        ResponseEntity<OrderDtoResponse> response = orderController.createOrder(orderDtoRequest);

        assertNotNull(response.getBody());
        assertEquals(1140.0, response.getBody().getTotal());
        verify(orderService, times(1)).createOrder(orderDtoRequest);
    }

    @Test
    void testGetOrderById() {
        when(orderService.getOrderById(1L)).thenReturn(orderDtoResponse);

        ResponseEntity<OrderDtoResponse> response = orderController.getOrderById(1L);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void testConfirmOrder() {
        when(orderService.confirmOrder(1L)).thenReturn(orderDtoResponse);

        ResponseEntity<OrderDtoResponse> response = orderController.confirmOrder(1L);

        assertNotNull(response.getBody());
        assertEquals(1140.0, response.getBody().getTotal());
        verify(orderService, times(1)).confirmOrder(1L);
    }


}
