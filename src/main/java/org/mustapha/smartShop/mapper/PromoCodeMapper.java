package org.mustapha.smartShop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mustapha.smartShop.dto.request.PromoCodeDtoRequest;
import org.mustapha.smartShop.dto.response.PromoCodeDtoResponse;
import org.mustapha.smartShop.model.PromoCode;

@Mapper(componentModel = "spring")
public interface PromoCodeMapper {

    PromoCode toEntity(PromoCodeDtoRequest dto);


    PromoCodeDtoResponse toDto(PromoCode promoCode);
}
