package org.mustapha.smartShop.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.mustapha.smartShop.enums.PaymentType;

import java.time.LocalDate;

@Data
public class PaymentDtoRequest {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @Min(value = 1, message = "Amount must be greater than 0")
    private double amount;

    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;

    private LocalDate paymentDate;      // optional (if null → now)
    private LocalDate encashmentDate;   // optional
}
