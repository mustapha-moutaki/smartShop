package org.mustapha.smartShop.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.mustapha.smartShop.model.PromoCode;

import java.util.List;

@Data
public class OrderDtoRequest {

    @NotNull(message = "Client id is required")
    private Long clientId;

    @NotNull
    private List<OrderItemDtoRequest> items;

    private Long promoCodeId;




}
