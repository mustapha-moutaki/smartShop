package org.mustapha.smartShop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mustapha.smartShop.dto.request.PaymentDtoRequest;
import org.mustapha.smartShop.dto.response.PaymentDtoResponse;
import org.mustapha.smartShop.enums.PaymentType;
import org.mustapha.smartShop.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private PaymentDtoRequest paymentDtoRequest;
    private PaymentDtoResponse paymentDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize request
        paymentDtoRequest = new PaymentDtoRequest();
        paymentDtoRequest.setOrderId(1L);
        paymentDtoRequest.setAmount(100.0);
        paymentDtoRequest.setPaymentType(PaymentType.CASH);
        paymentDtoRequest.setPaymentDate(LocalDate.now());
        paymentDtoRequest.setEncashmentDate(LocalDate.now());


        // Initialize response
        paymentDtoResponse = new PaymentDtoResponse();
        paymentDtoResponse.setId(1L);
        paymentDtoResponse.setOrderId(1L);
        paymentDtoResponse.setPaymentNumber(1);
        paymentDtoResponse.setAmount(100.0);
        paymentDtoResponse.setPaymentDate(LocalDate.now());
        paymentDtoResponse.setEncashmentDate(LocalDate.now());
    }

    @Test
    void testMakePayment() {
        // Mock service
        when(paymentService.makePayment(any(PaymentDtoRequest.class))).thenReturn(paymentDtoResponse);

        // Call controller
        ResponseEntity<PaymentDtoResponse> response = paymentController.makePayment(paymentDtoRequest);

        // Assertions
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(paymentDtoResponse, response.getBody());

        // Verify service call
        verify(paymentService, times(1)).makePayment(any(PaymentDtoRequest.class));
    }
}
