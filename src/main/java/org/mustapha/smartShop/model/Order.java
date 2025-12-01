package org.mustapha.smartShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mustapha.smartShop.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // single order contains many orderItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    private LocalDateTime date = LocalDateTime.now();

    private double subTotal;

    private double discount;

    private double vat;

    private double total;

    @OneToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;

    private OrderStatus status = OrderStatus.PENDING;

    private double remainingAmount;


}
