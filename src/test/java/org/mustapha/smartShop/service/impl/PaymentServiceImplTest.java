package org.mustapha.smartShop.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mustapha.smartShop.dto.request.PaymentDtoRequest;
import org.mustapha.smartShop.dto.response.PaymentDtoResponse;
import org.mustapha.smartShop.enums.OrderStatus;
import org.mustapha.smartShop.enums.PaymentStatus;
import org.mustapha.smartShop.exception.BusinessRuleException;
import org.mustapha.smartShop.exception.ResourceNotFoundException;
import org.mustapha.smartShop.mapper.PaymentMapper;
import org.mustapha.smartShop.model.Order;
import org.mustapha.smartShop.model.Payment;
import org.mustapha.smartShop.repository.OrderRepository;
import org.mustapha.smartShop.repository.PaymentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private PaymentDtoRequest paymentDtoRequest;
    private Payment payment;
    private PaymentDtoResponse paymentDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize order
        order = new Order();
        order.setId(1L);
        order.setTotal(1000.0);
        order.setRemainingAmount(1000.0);
        order.setStatus(OrderStatus.PENDING);

        // Initialize payment request
        paymentDtoRequest = new PaymentDtoRequest();
        paymentDtoRequest.setOrderId(1L);
        paymentDtoRequest.setAmount(200.0);
        paymentDtoRequest.setPaymentDate(LocalDate.now());
        paymentDtoRequest.setEncashmentDate(null);

        // Initialize payment entity
        payment = new Payment();
        payment.setId(1L);
        payment.setPaymentNumber(1);
        payment.setAmount(200.0);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setOrder(order);

        // Initialize payment response
        paymentDtoResponse = new PaymentDtoResponse();
        paymentDtoResponse.setId(1L);
        paymentDtoResponse.setPaymentNumber(1);
        paymentDtoResponse.setAmount(200.0);
        paymentDtoResponse.setPaymentStatus(PaymentStatus.PENDING);
    }

    @Test
    void testMakePaymentSuccess() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrOrderId(1L)).thenReturn(new ArrayList<>());
        when(paymentMapper.toEntity(paymentDtoRequest)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(paymentDtoResponse);

        PaymentDtoResponse response = paymentService.makePayment(paymentDtoRequest);

        assertNotNull(response);
        assertEquals(1, response.getPaymentNumber());
        assertEquals(200.0, response.getAmount());
        assertEquals(PaymentStatus.PENDING, response.getPaymentStatus());
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testMakePaymentOverPaymentThrowsException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        List<Payment> previous = new ArrayList<>();
        Payment prevPayment = new Payment();
        prevPayment.setAmount(900.0);
        previous.add(prevPayment);
        when(paymentRepository.findByOrOrderId(1L)).thenReturn(previous);

        paymentDtoRequest.setAmount(200.0); // exceeds remaining (1000-900=100)

        assertThrows(BusinessRuleException.class, () -> paymentService.makePayment(paymentDtoRequest));
    }

    @Test
    void testMakePaymentOrderNotFoundThrowsException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.makePayment(paymentDtoRequest));
    }
}
