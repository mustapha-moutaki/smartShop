package org.mustapha.smartShop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mustapha.smartShop.dto.request.OrderDtoRequest;
import org.mustapha.smartShop.dto.request.OrderItemDtoRequest;
import org.mustapha.smartShop.dto.response.OrderDtoResponse;
import org.mustapha.smartShop.enums.LoyaltyLevel;
import org.mustapha.smartShop.enums.OrderStatus;
import org.mustapha.smartShop.mapper.OrderMapper;
import org.mustapha.smartShop.model.Client;
import org.mustapha.smartShop.model.Order;
import org.mustapha.smartShop.model.OrderItem;
import org.mustapha.smartShop.model.Product;
import org.mustapha.smartShop.repository.ClientRepository;
import org.mustapha.smartShop.repository.OrderRepository;
import org.mustapha.smartShop.repository.ProductRepository;
import org.mustapha.smartShop.repository.PromoCodeRepository;
import org.mustapha.smartShop.service.impl.OrderServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Mock
    private OrderMapper orderMapper;

    private Client client;
    private Product product;
    private OrderDtoRequest orderDtoRequest;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setLoyaltyLevel(LoyaltyLevel.SILVER);

        product = new Product();
        product.setId(1L);
        product.setUnitPrice(100.0);
        product.setStock(50);

        OrderItemDtoRequest itemDto = new OrderItemDtoRequest();
        itemDto.setProductId(product.getId());
        itemDto.setQuantity(2);

        orderDtoRequest = new OrderDtoRequest();
        orderDtoRequest.setClientId(client.getId());
        orderDtoRequest.setItems(List.of(itemDto));
    }

    @Test
    void createOrder_ShouldReturnOrderDtoResponse_WhenOrderIsValid() {
        // Mock client repository
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        // Mock product repository
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // Mock mapper
        Order orderEntity = new Order();
        when(orderMapper.toEntity(orderDtoRequest)).thenReturn(orderEntity);
        OrderDtoResponse orderDtoResponse = new OrderDtoResponse();
        when(orderMapper.toDto(orderEntity)).thenReturn(orderDtoResponse);

        OrderDtoResponse response = orderService.createOrder(orderDtoRequest);

        // Verify interactions
        verify(orderRepository, times(1)).save(orderEntity);
        assertNotNull(response);
        assertEquals(orderDtoResponse, response);
    }

    @Test
    void createOrder_ShouldThrowException_WhenClientNotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> orderService.createOrder(orderDtoRequest));
    }

    @Test
    void confirmOrder_ShouldThrowBusinessRuleException_WhenRemainingAmountIsNotZero() {
        Order order = new Order();
        order.setId(1L);
        order.setRemainingAmount(100.0);
        order.setItems(List.of());
        order.setClient(client);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(RuntimeException.class, () -> orderService.confirmOrder(1L));
    }
}
