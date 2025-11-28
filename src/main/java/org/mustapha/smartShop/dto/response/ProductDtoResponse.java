package org.mustapha.smartShop.dto.response;

import lombok.Data;

@Data
public class ProductDtoResponse {

    private Long id;
    private String name;
    private double unitPrice;
    private int stock;
}
