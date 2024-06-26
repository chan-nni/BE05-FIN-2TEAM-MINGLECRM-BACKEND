package com.team2final.minglecrm.payment.domain;

import com.team2final.minglecrm.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String consumeType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String paymentSpot;

    //TODO : reward 취소하는 로직 작성
    public void cancelReservation(boolean cancel) {
        this.isRefunded = cancel;
        this.refundDate = LocalDateTime.now();
    }
}
