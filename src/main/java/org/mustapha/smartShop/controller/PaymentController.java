package org.mustapha.smartShop.controller;

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
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDtoResponse>  makePayment(@Valid @RequestBody PaymentDtoRequest paymentDtoRequest){
        PaymentDtoResponse madedpayment = paymentService.makePayment(paymentDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(madedpayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PaymentDtoResponse>> getAll(@PathVariable Long id) {

        List<PaymentDtoResponse> response = paymentService.getPaymentsByOrder(id);

        return ResponseEntity.ok(response);
    }

}
