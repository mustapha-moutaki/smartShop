package org.mustapha.smartShop.dto.response;

import lombok.Data;

@Data
public class OrderItemDtoResponse {
    private Long productId;

    private String productName;

    private int quantity;

    private double unitPrice;

    private double totalLine;
}
