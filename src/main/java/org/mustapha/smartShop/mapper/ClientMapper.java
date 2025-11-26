package org.mustapha.smartShop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mustapha.smartShop.dto.request.ClientDtoRequest;
import org.mustapha.smartShop.dto.response.ClientDtoResponse;
import org.mustapha.smartShop.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "orderIds", ignore = true) // we ignore ordersIds to relate it in the servie
    @Mapping(target = "user", ignore = true)
    Client toEntity(ClientDtoRequest clientDtoRequest);


    @Mapping(target = "orderIds", expression = "java(client.getOrderList() == null ? null : client.getOrderList().stream().map(o -> o.getId()).collect(Collectors.toList()))")
    @Mapping(target = "username", expression = "java(client.getUser().getUsername())")
    @Mapping(target = "role", expression = "java(client.getUser().getRole())")

    ClientDtoResponse toDto(Client client);

}
