package org.mustapha.smartShop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.PaymentDtoRequest;
import org.mustapha.smartShop.dto.response.PaymentDtoResponse;
import org.mustapha.smartShop.model.Payment;
import org.mustapha.smartShop.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Operations related to payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Make a payment", description = "Create a new payment for an order")
    public ResponseEntity<PaymentDtoResponse>  makePayment(@Valid @RequestBody PaymentDtoRequest paymentDtoRequest){
        PaymentDtoResponse madedpayment = paymentService.makePayment(paymentDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(madedpayment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payments by order ID", description = "Retrieve all payments related to a specific order")
    public ResponseEntity<List<PaymentDtoResponse>> getAll(@PathVariable Long id) {

        List<PaymentDtoResponse> response = paymentService.getPaymentsByOrder(id);

        return ResponseEntity.ok(response);
    }

}
