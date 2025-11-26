package org.mustapha.smartShop.model;

import jakarta.persistence.*;
import lombok.Data;
import org.mustapha.smartShop.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // many prodcuts has many orders and many order for same order
    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product>products = new ArrayList<>();

    private LocalDateTime date;

    private double subTotal;

    private double discount;

    private double vat;

    private double total;

    @OneToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;

    private OrderStatus status = OrderStatus.PENDING;

    private double RemainingAmount;


}
