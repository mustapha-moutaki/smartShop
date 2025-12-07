package org.mustapha.smartShop.dto.request;

import lombok.Data;

@Data
public class PromoCodeDtoRequest {
        private String code;
        private double percentage;
        private boolean isActive = true;
    }


