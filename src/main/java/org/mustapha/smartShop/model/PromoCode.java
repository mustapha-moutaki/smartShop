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

    @Column(columnDefinition = "boolean default true")
    private boolean isActive = true;

}
