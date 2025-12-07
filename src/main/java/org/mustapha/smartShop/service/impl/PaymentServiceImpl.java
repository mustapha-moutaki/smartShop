package org.mustapha.smartShop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.PaymentDtoRequest;
import org.mustapha.smartShop.dto.response.PaymentDtoResponse;
import org.mustapha.smartShop.enums.OrderStatus;
import org.mustapha.smartShop.enums.PaymentStatus;
import org.mustapha.smartShop.exception.BusinessRuleException;
import org.mustapha.smartShop.exception.ResourceNotFoundException;
import org.mustapha.smartShop.mapper.PaymentMapper;
import org.mustapha.smartShop.model.Order;
import org.mustapha.smartShop.model.Payment;
import org.mustapha.smartShop.repository.OrderRepository;
import org.mustapha.smartShop.repository.PaymentRepository;
import org.mustapha.smartShop.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDtoResponse makePayment(PaymentDtoRequest dto) {
        // 1- chekc order exist before payment
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                ()-> new ResourceNotFoundException("order not found")
        );

        //2 - getting previous payments of that order
        List<Payment> previousPayments = paymentRepository.findByOrOrderId(dto.getOrderId());

        //3- compute next payment number
        int nextPaymentNumber = previousPayments.isEmpty() ? 1
              : previousPayments.stream()
               .max(Comparator.comparing(p-> p.getPaymentNumber()))
                .get().getPaymentNumber()+1;

        //4- compte remianing amount
        double alreadyPaind = previousPayments.stream()
                .mapToDouble(payment -> payment.getAmount())
                .sum();

        double remaining = order.getTotal()- alreadyPaind;

        // 5- check if payment exceeds remaining
        if(dto.getAmount() > remaining){
            throw  new BusinessRuleException("payment exceeds remaining amount");
        }
        // convert to entity
        Payment payment = paymentMapper.toEntity(dto);
        // handle payment and order and all we didn't hndle in mapper
        payment.setOrder(order);
        payment.setPaymentNumber(nextPaymentNumber);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : LocalDate.now());

        // 7- if payment is fully encashed
        if(dto.getEncashmentDate() != null){
            payment.setEncashmentDate(dto.getEncashmentDate());
            payment.setPaymentStatus(PaymentStatus.RECEIVED);
        }
        Payment savedPayment = paymentRepository.save(payment);

        //8- update order Remaining & status
        double newRemaining = remaining - dto.getAmount();
        order.setRemainingAmount(newRemaining);

        if(remaining == 0){
            order.setStatus(OrderStatus.CONFIRMED);
        }else{
            order.setStatus(OrderStatus.PENDING);
        }

        orderRepository.save(order);

        // 9 - return response
        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public List<PaymentDtoResponse> getPaymentsByOrder(Long orderId) {
        return paymentRepository.findByOrOrderId(orderId).stream()
                .map(p->paymentMapper.toDto(p))
                .toList();
    }
}
