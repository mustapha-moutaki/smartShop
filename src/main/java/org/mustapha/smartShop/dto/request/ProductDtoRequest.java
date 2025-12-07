package org.mustapha.smartShop.dto.request;

import lombok.Data;

@Data
public class ProductDtoRequest {
    private String name;
    private double unitPrice;
    private int stock;
    private boolean deleted;
}
