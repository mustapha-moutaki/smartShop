package org.mustapha.smartShop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.config.AppConstants;
import org.mustapha.smartShop.dto.request.OrderDtoRequest;
import org.mustapha.smartShop.dto.response.ClientOrderDtoResponse;
import org.mustapha.smartShop.dto.response.OrderDtoResponse;
import org.mustapha.smartShop.enums.LoyaltyLevel;
import org.mustapha.smartShop.enums.OrderStatus;
import org.mustapha.smartShop.exception.BusinessRuleException;
import org.mustapha.smartShop.exception.ResourceNotFoundException;
import org.mustapha.smartShop.mapper.OrderMapper;
import org.mustapha.smartShop.model.Client;
import org.mustapha.smartShop.model.Order;
import org.mustapha.smartShop.model.OrderItem;
import org.mustapha.smartShop.model.Product;
import org.mustapha.smartShop.model.PromoCode;
import org.mustapha.smartShop.repository.ClientRepository;
import org.mustapha.smartShop.repository.OrderRepository;
import org.mustapha.smartShop.repository.ProductRepository;
import org.mustapha.smartShop.repository.PromoCodeRepository;
import org.mustapha.smartShop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final PromoCodeRepository promoCodeRepository;

    @Override
    public OrderDtoResponse createOrder(OrderDtoRequest orderDtoRequest) {

        // Fetch client from DB
        Client client = clientRepository.findById(orderDtoRequest.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + orderDtoRequest.getClientId()));

        // Convert DTO to Order entity
        Order order = orderMapper.toEntity(orderDtoRequest);
        order.setClient(client); // link client to the order

        // Fetch promo code if exists
        if (orderDtoRequest.getPromoCodeId() != null && orderDtoRequest.getPromoCodeId() != null) {
            PromoCode promoCode = promoCodeRepository.findById(orderDtoRequest.getPromoCodeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Promo code not found with id: " + orderDtoRequest.getPromoCodeId()));
            order.setPromoCode(promoCode);
        }

        // Map OrderItem DTOs to OrderItem entities
        order.setItems(orderDtoRequest.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));

                    OrderItem item = new OrderItem();
                    item.setProduct(product);
                    item.setQuantity(itemDto.getQuantity());
                    item.setUnitPrice(product.getUnitPrice());
                    item.setOrder(order);
                    return item;
                }).collect(Collectors.toList())
        );

        //  Calculate Subtotal
        double subTotal = order.getItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
        order.setSubTotal(subTotal);

        // Calculate discount (loyalty + promo)
        double discountAmount = getDiscountAmount(order, subTotal);
        order.setDiscount(discountAmount);

        // Price after discount
        double priceAfterDiscount = subTotal - discountAmount;

        // Calculate VAT
        double tvaAmount = priceAfterDiscount * AppConstants.TVA;
        order.setVat(tvaAmount);

        // Calculate total and remaining amount
        double totalPrice = priceAfterDiscount + tvaAmount;
        order.setTotal(totalPrice);
        order.setRemainingAmount(totalPrice);

        // Set initial order status
        order.setStatus(OrderStatus.PENDING);

        // Save order to DB
        orderRepository.save(order);

        // Convert entity to DTO for response
        return orderMapper.toDto(order);
    }

    private static double getDiscountAmount(Order order, double subTotal) {
        double loyaltyDiscountRate = 0.0;

        LoyaltyLevel loyaltyLevel = order.getClient().getLoyaltyLevel(); // BASIC, SILVER, GOLD, PLATINUM

        if (loyaltyLevel == LoyaltyLevel.SILVER && subTotal >= 500) loyaltyDiscountRate = 0.05;
        else if (loyaltyLevel == LoyaltyLevel.GOLD && subTotal >= 800) loyaltyDiscountRate = 0.10;
        else if (loyaltyLevel == LoyaltyLevel.PLATINUM && subTotal >= 1200) loyaltyDiscountRate = 0.15;

        // Promo discount
        double promoRate = 0.0;
        if (order.getPromoCode() != null && order.getPromoCode().isActive()) {
            promoRate = order.getPromoCode().getPercentage() / 100.0;
        }

        // Total discount = loyalty + promo
        double totalDiscountRate = loyaltyDiscountRate + promoRate;
        return subTotal * totalDiscountRate;
    }

    @Override
    public OrderDtoResponse updateOrder(Long id, OrderDtoRequest orderDtoRequest) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // Update items
        order.setItems(
                orderDtoRequest.getItems().stream()
                        .map(itemDto -> {
                            Product product = productRepository.findById(itemDto.getProductId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));

                            OrderItem item = new OrderItem();
                            item.setProduct(product);
                            item.setQuantity(itemDto.getQuantity());
                            item.setUnitPrice(product.getUnitPrice());
                            item.setOrder(order);
                            return item;
                        })
                        .collect(Collectors.toList())
        );

        // Recalculate amounts
        double subTotal = order.getItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
        order.setSubTotal(subTotal);

        double discountAmount = getDiscountAmount(order, subTotal);
        order.setDiscount(discountAmount);

        double priceAfterDiscount = subTotal - discountAmount;
        double tvaAmount = priceAfterDiscount * AppConstants.TVA;
        order.setVat(tvaAmount);
        order.setTotal(priceAfterDiscount + tvaAmount);
        order.setRemainingAmount(order.getTotal());

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Override
    public OrderDtoResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDtoResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setStatus(OrderStatus.CANCELED);
    }
    // confiremd order --
    @Override
    public OrderDtoResponse confirmOrder(Long id) {
        // Fetch the order by ID from the database
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // Check if the order has any remaining amount to be paid
        if (order.getRemainingAmount() > 0) {
            throw new BusinessRuleException("Cannot confirm order. Remaining amount: " + order.getRemainingAmount());
        }

        // Set the order status to CONFIRMED
        order.setStatus(OrderStatus.CONFIRMED);

        // Update the stock of products based on the quantities in the order
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + item.getProduct().getId()));
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Update the client's loyalty level based on confirmed orders and total spending
        updateClientLoyaltyLevel(order);

        // Save the confirmed order
        orderRepository.save(order);

        // Convert the order entity to DTO and return it
        return orderMapper.toDto(order);
    }

    // Helper method to update the client's loyalty level
    private void updateClientLoyaltyLevel(Order order) {
        Client client = order.getClient();
        if (client == null) return;

        // Count the number of confirmed orders for the client
        long confirmedOrdersCount = orderRepository.countByClientIdAndStatus(client.getId(), OrderStatus.CONFIRMED);

        // Calculate the total amount of confirmed orders for the client
        double totalConfirmedAmount = orderRepository.sumTotalByClientIdAndStatus(client.getId(), OrderStatus.CONFIRMED);

        // Determine the loyalty level based on confirmed orders or total spending
        if (confirmedOrdersCount >= 20 || totalConfirmedAmount >= 15000) {
            client.setLoyaltyLevel(LoyaltyLevel.PLATINUM);
        } else if (confirmedOrdersCount >= 10 || totalConfirmedAmount >= 5000) {
            client.setLoyaltyLevel(LoyaltyLevel.GOLD);
        } else if (confirmedOrdersCount >= 3 || totalConfirmedAmount >= 1000) {
            client.setLoyaltyLevel(LoyaltyLevel.SILVER);
        } else {
            client.setLoyaltyLevel(LoyaltyLevel.BRONZE);
        }

        // Save the updated client with the new loyalty level
        clientRepository.save(client);
    }

    @Override
    public List<Order> findOrdersByClientId(Long id) {
        return orderRepository.findByClient_Id(id);
    }
}


