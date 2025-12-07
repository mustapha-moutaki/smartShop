package org.mustapha.smartShop.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ClientOrderDtoResponse {

    private Long id;

    private List<Long> itemIds;

    private LocalDateTime date;

    private double subTotal;

    private double discount;

    private double vat;

    private double total;
}
