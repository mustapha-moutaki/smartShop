package org.mustapha.smartShop.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mustapha.smartShop.enums.OrderStatus;
import org.mustapha.smartShop.model.Client;
import org.mustapha.smartShop.model.Product;
import org.mustapha.smartShop.model.PromoCode;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderItemDtoRequest {


    @NotNull(message = "Product id is required")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

}
