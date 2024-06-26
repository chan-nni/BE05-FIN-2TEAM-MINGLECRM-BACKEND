package com.team2final.minglecrm.payment.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentSearchCondition {

    private String customerName;
    private String number;
    private String type;
    private Long paymentAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}