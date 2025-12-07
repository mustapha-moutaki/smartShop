package org.mustapha.smartShop.service;

import org.mustapha.smartShop.dto.request.PaymentDtoRequest;
import org.mustapha.smartShop.dto.response.PaymentDtoResponse;

import java.util.List;

public interface PaymentService {

    PaymentDtoResponse makePayment(PaymentDtoRequest dto);

    List<PaymentDtoResponse> getPaymentsByOrder(Long orderId);

}
