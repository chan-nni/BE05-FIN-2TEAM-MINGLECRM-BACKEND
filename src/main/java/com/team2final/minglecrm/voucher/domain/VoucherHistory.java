package com.team2final.minglecrm.voucher.domain;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.voucher.domain.status.VoucherStatusType;
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
public class VoucherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Enumerated(value = EnumType.STRING)
    private VoucherStatusType status;
    private LocalDateTime requestedDate; // 승인 요청 일시
    private LocalDateTime confirmedDate; // 승인/거절 일시
    private LocalDateTime issueOrCancelDate; // 이메일 발송 일시
    private LocalDateTime convertedDate; // 리워드 전환 일시

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id", nullable = true)
    private Employee employeeManager;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "staff_id")
    private Employee employeeStaff;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String voucherCode;
    private String rejectedReason;

    @Builder
    public VoucherHistory(Voucher voucher, VoucherStatusType status, LocalDateTime requestedDate, LocalDateTime issueOrCancelDate, LocalDateTime confirmedDate, LocalDateTime convertedDate, Employee employeeManager, Employee employeeStaff, Customer customer, String voucherCode, String rejectedReason) {
        this.voucher = voucher;

        this.status = status;

        this.requestedDate = requestedDate;
        this.confirmedDate = confirmedDate;
        this.issueOrCancelDate = issueOrCancelDate;
        this.convertedDate = convertedDate;

        this.employeeManager = employeeManager;
        this.employeeStaff = employeeStaff;
        this.customer = customer;

        this.voucherCode = voucherCode;
        this.rejectedReason = rejectedReason;
    }

    public void approveVoucher(Employee approver, String generatedUniqueVoucherCode) {
        this.confirmedDate = LocalDateTime.now();
        this.employeeManager = approver;
        this.status = VoucherStatusType.APPROVED;
        this.voucherCode = generatedUniqueVoucherCode;
    }

    public void rejectVoucher(String rejectedReason, Employee employeeManager) {
        this.status = VoucherStatusType.REJECTED;
        this.rejectedReason = rejectedReason;
        this.confirmedDate = LocalDateTime.now();
        this.employeeManager = employeeManager;
    }

    public void sendVoucherEmail() {
        this.status = VoucherStatusType.SENDED;
        this.issueOrCancelDate = LocalDateTime.now();
    }

    public void cancelVoucher() {
        this.status = VoucherStatusType.CANCELED;
        this.issueOrCancelDate = LocalDateTime.now();
    }
}