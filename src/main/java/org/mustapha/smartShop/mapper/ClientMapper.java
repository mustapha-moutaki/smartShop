package org.mustapha.smartShop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mustapha.smartShop.dto.request.ClientDtoRequest;
import org.mustapha.smartShop.dto.response.ClientDtoResponse;
import org.mustapha.smartShop.model.Client;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    @Mapping(target = "user", ignore = true)
    Client toEntity(ClientDtoRequest clientDtoRequest);

    @Mapping(target = "orderIds", expression = "java(client.getOrderList() == null ? null : client.getOrderList().stream().map(o -> o.getId()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "username", expression = "java(client.getUser().getUsername())")
    @Mapping(target = "role", expression = "java(client.getUser().getRole())")
    ClientDtoResponse toDto(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateClientFromDto(ClientDtoRequest dto, @MappingTarget Client entity);
}