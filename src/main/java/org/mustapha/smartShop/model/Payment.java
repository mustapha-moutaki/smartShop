package org.mustapha.smartShop.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mustapha.smartShop.enums.PaymentStatus;
import org.mustapha.smartShop.enums.PaymentType;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int paymentNumber;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private LocalDate paymentDate;     // payment date
    private LocalDate encashmentDate;  // getting shqu date

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
