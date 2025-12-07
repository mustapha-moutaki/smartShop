//package org.mustapha.smartShop.mapper;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//import org.mustapha.smartShop.dto.request.OrderDtoRequest;
//import org.mustapha.smartShop.dto.response.OrderDtoResponse;
//import org.mustapha.smartShop.model.Order;
//
//@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
//public interface OrderMapper {
//
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "promoCode", source = "promoCode")
//    Order toEntity(OrderDtoRequest dto);
//
//    @Mapping(source = "promoCode.id", target = "promoCodeId")
//    OrderDtoResponse toDto(Order order);
//}
package org.mustapha.smartShop.mapper;

import org.mustapha.smartShop.dto.request.OrderDtoRequest;
import org.mustapha.smartShop.dto.response.OrderDtoResponse;
import org.mustapha.smartShop.dto.response.OrderItemDtoResponse;
import org.mustapha.smartShop.model.Order;
import org.mustapha.smartShop.model.OrderItem;
import org.mustapha.smartShop.model.Product;
import org.mustapha.smartShop.model.Client;
import org.mustapha.smartShop.model.PromoCode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    // DTO -> Entity
    public Order toEntity(OrderDtoRequest dto) {
        if (dto == null) return null;

        Order order = new Order();
        order.setDate(LocalDateTime.now());

        // Set client
        if (dto.getClientId() != null) {
            Client client = new Client();
            client.setId(dto.getClientId()); // only set ID, JPA will manage
            order.setClient(client);
        }

        // Set promo code
        if (dto.getPromoCodeId() != null) {
            PromoCode promoCode = new PromoCode();
            promoCode.setId(dto.getPromoCodeId());
            order.setPromoCode(promoCode);
        }

        // Set items
        if (dto.getItems() != null) {
            order.setItems(
                    dto.getItems().stream().map(itemDto -> {
                        OrderItem item = new OrderItem();
                        Product product = new Product();
                        product.setId(itemDto.getProductId()); // only ID
                        item.setProduct(product);
                        item.setQuantity(itemDto.getQuantity());
                        item.setUnitPrice(0); // will be recalculated in service
                        item.setOrder(order);
                        return item;
                    }).collect(Collectors.toList())
            );
        }

        return order;
    }

    // Entity -> DTO
    public OrderDtoResponse toDto(Order order) {
        if (order == null) return null;

        OrderDtoResponse dto = new OrderDtoResponse();
        dto.setId(order.getId());

        // client info
        if (order.getClient() != null) {
            dto.setClientId(order.getClient().getId());
            dto.setClientName(order.getClient().getName());
        }

        // items
        dto.setItems(order.getItems().stream().map(item -> {
            OrderItemDtoResponse itemDto = new OrderItemDtoResponse();
            if (item.getProduct() != null) {
                itemDto.setProductId(item.getProduct().getId());
                itemDto.setProductName(item.getProduct().getName());
            }
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            itemDto.setTotalLine(item.getUnitPrice() * item.getQuantity());
            return itemDto;
        }).collect(Collectors.toList()));

        dto.setDate(order.getDate());
        dto.setSubTotal(order.getSubTotal());
        dto.setDiscount(order.getDiscount());
        dto.setVat(order.getVat());
        dto.setTotal(order.getTotal());
        dto.setPromoCodeId(order.getPromoCode() != null ? order.getPromoCode().getId() : 0);
        dto.setStatus(order.getStatus());
        dto.setRemainingAmount(order.getRemainingAmount());

        return dto;
    }
}
