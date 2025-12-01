package org.mustapha.smartShop.dto.response;

import lombok.Data;
import org.mustapha.smartShop.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDtoResponse {

    private Long id;

    private Long clientId;

    private String clientName;

    private List<OrderItemDtoResponse> items;

    private LocalDateTime date;

    private double subTotal;

    private double discount;

    private double vat;

    private double total;

    private long promoCodeId;

    private OrderStatus status;

    private double remainingAmount;
}
