package org.mustapha.smartShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "promocodes")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private double percentage;

    private boolean isActive;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
