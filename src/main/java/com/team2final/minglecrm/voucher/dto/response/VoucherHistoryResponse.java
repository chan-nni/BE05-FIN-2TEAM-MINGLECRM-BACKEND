package com.team2final.minglecrm.voucher.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.voucher.domain.VoucherHistory;
import com.team2final.minglecrm.voucher.domain.status.VoucherStatusType;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class VoucherHistoryResponse {

    private final Long voucherHistoryId;
    private final Long voucherId;

    private final VoucherStatusType status;

    private final LocalDateTime requestDate;
    private final String createdReason;

    private final LocalDateTime confirmDate;
    private final LocalDateTime conversionDate;

    private final Long creatorId;
    private final String creatorName;

    private final Long confirmerId;
    private final String confirmerName;

    private final Long customerId;
    private final String customerName;
    private final String customerEmail;

    private final Long amount;
    private final String rejectedReason;
    private final String voucherCode;
    private final LocalDateTime sendOrCancelDate;

    private final String customerGrade;

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public static VoucherHistoryResponse of(VoucherHistory voucherHistory) {
        Long employeeStaffId = voucherHistory.getEmployeeStaff() != null ? voucherHistory.getEmployeeStaff().getId() : null;
        Long employeeManagerId = voucherHistory.getEmployeeManager() != null ? voucherHistory.getEmployeeManager().getId() : null;
        String employeeStaffName = voucherHistory.getEmployeeStaff() != null ? voucherHistory.getEmployeeStaff().getName() : null;
        String employeeManagerName = voucherHistory.getEmployeeManager() != null ? voucherHistory.getEmployeeManager().getName() : null;


        return new VoucherHistoryResponse(
                voucherHistory.getId(),
                voucherHistory.getVoucher().getId(),
                voucherHistory.getStatus(),
                voucherHistory.getRequestedDate(),
                voucherHistory.getVoucher().getCreatedReason(),
                voucherHistory.getConfirmedDate(),
                voucherHistory.getConvertedDate(),

                employeeStaffId,
                employeeStaffName,

                employeeManagerId,
                employeeManagerName,

                voucherHistory.getCustomer().getId(),
                voucherHistory.getCustomer().getName(),
                voucherHistory.getCustomer().getEmail(),

                voucherHistory.getVoucher().getAmount(),
                voucherHistory.getRejectedReason(),
                voucherHistory.getVoucherCode(),
                voucherHistory.getIssueOrCancelDate(),

                // 고객등급
                voucherHistory.getCustomer().getGrade(),

                // 바우처 유효기간
                voucherHistory.getVoucher().getStartDate(),
                voucherHistory.getVoucher().getEndDate()
        );
    }

    @QueryProjection
    public VoucherHistoryResponse(Long voucherHistoryId, Long voucherId, VoucherStatusType status, LocalDateTime requestDate, String createdReason, LocalDateTime confirmDate, LocalDateTime conversionDate, Long creatorId, String creatorName, Long confirmerId, String confirmerName, Long customerId, String customerName, String customerEmail, Long amount, String rejectedReason, String voucherCode, LocalDateTime sendOrCancelDate, String customerGrade, LocalDateTime startDate, LocalDateTime endDate) {
        this.voucherHistoryId = voucherHistoryId;
        this.voucherId = voucherId;
        this.status = status;
        this.requestDate = requestDate;
        this.createdReason = createdReason;
        this.confirmDate = confirmDate;
        this.conversionDate = conversionDate;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.confirmerId = confirmerId;
        this.confirmerName = confirmerName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.amount = amount;
        this.rejectedReason = rejectedReason;
        this.voucherCode = voucherCode;
        this.sendOrCancelDate = sendOrCancelDate;
        this.customerGrade = customerGrade;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}