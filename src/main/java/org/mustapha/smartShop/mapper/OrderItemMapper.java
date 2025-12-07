package org.mustapha.smartShop.mapper;

import org.mapstruct.Mapper;
import org.mustapha.smartShop.dto.request.OrderItemDtoRequest;
import org.mustapha.smartShop.dto.response.OrderItemDtoResponse;
import org.mustapha.smartShop.model.OrderItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {


    // convert dtoRequest to entity
    OrderItem toEntity(OrderItemDtoRequest dto);

    // convert entity to dto
    OrderItemDtoResponse toDto(OrderItem orderItem);

}
