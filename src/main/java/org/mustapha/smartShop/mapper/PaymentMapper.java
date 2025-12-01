package org.mustapha.smartShop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mustapha.smartShop.dto.request.PaymentDtoRequest;
import org.mustapha.smartShop.dto.response.PaymentDtoResponse;
import org.mustapha.smartShop.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true) // relate between them in service
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "paymentNumber", ignore = true) // calcul it in service
    Payment toEntity(PaymentDtoRequest paymentDtoRequest);

    // give value of order.id that we have in db to orderId in dtoREsponse
    @Mapping(source = "order.id", target = "orderId")
    PaymentDtoResponse toDto(Payment payment);
}
