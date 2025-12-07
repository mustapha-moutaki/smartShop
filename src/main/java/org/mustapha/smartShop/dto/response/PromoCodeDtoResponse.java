package org.mustapha.smartShop.dto.response;

import lombok.Data;

@Data
public class PromoCodeDtoResponse {
    private Long id;
    private String code;
    private double percentage;
    private boolean isActive;
}
