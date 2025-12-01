package org.mustapha.smartShop.dto.response;

import lombok.Data;
import org.mustapha.smartShop.enums.PaymentStatus;
import org.mustapha.smartShop.enums.PaymentType;

import java.time.LocalDate;

@Data
public class PaymentDtoResponse {

    private Long id;

    private Long orderId;

    private int paymentNumber;

    private double amount;

    private PaymentType paymentType;

    private LocalDate paymentDate;

    private LocalDate encashmentDate;

    private PaymentStatus paymentStatus;
}
