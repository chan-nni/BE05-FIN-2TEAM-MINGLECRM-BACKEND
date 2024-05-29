package com.team2final.minglecrm.entity.payment;

import com.team2final.minglecrm.entity.customer.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    private String type;
    private Long amountBeforeDiscount;
    private Long discountAmount;
    private Long paymentAmount;
    private LocalDateTime paymentDate;
    private Boolean isRefunded;
    private LocalDateTime refundDate;
    private Long createdReward;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String paymentSpot;








}
