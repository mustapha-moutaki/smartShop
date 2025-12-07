package org.mustapha.smartShop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.*;
import org.mustapha.smartShop.dto.request.UserDtoRequest;
import org.mustapha.smartShop.dto.response.UserDtoResponse;
import org.mustapha.smartShop.model.User;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface UserMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true) // if the client is null we ignore that because we handle it in the service
    User toEntity(UserDtoRequest userDtoRequest);

    UserDtoResponse toDto(User user);


}
