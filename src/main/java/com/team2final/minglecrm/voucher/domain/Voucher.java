package com.team2final.minglecrm.voucher.domain;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "creator_id")
    private Employee employee;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Long amount;
    private String createdReason;
    private LocalDateTime createdDate;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Boolean isRequested;

    @Builder
    public Voucher(Employee employee, Customer customer, Long amount, String createdReason, Boolean isRequested, LocalDateTime createdDate, LocalDateTime startDate, LocalDateTime endDate) {
        this.employee = employee;
        this.customer = customer;
        this.amount = amount;
        this.createdReason = createdReason;
        this.isRequested = isRequested;
        this.createdDate = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void requestVoucher(Voucher voucher) {
        this.isRequested = Boolean.TRUE;

    }
}