package org.mustapha.smartShop.mapper;

import org.mapstruct.Mapper;
import org.mustapha.smartShop.dto.request.ProductDtoRequest;
import org.mustapha.smartShop.dto.response.ProductDtoResponse;
import org.mustapha.smartShop.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDtoRequest productDtoRequest);
     ProductDtoResponse toDto(Product product);

}
