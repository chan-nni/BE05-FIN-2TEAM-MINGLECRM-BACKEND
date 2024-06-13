package com.team2final.minglecrm.controller.reward.response;

import com.team2final.minglecrm.entity.reward.Voucher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class VoucherResponse {
    private final Long voucherId;
    private final Long customerId;
    private final String customerName;
    private final Long employeeId;
    private final String employeeName;
    private final Long amount;
    private final LocalDateTime createdDate;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String createdReason;


    public static VoucherResponse of(Voucher voucher) {
        Long employeeId = voucher.getEmployee() != null ? voucher.getEmployee().getId() : null;
        String employeeName = voucher.getEmployee() != null ? voucher.getEmployee().getName() : null;

        return new VoucherResponse(
                voucher.getId(),
                voucher.getCustomer().getId(),
                voucher.getCustomer().getName(),
//                voucher.getEmployee().getId(),
                employeeId,
//                voucher.getEmployee().getName(),
                employeeName,
                voucher.getAmount(),
                voucher.getCreatedDate(),
                voucher.getStartDate(),
                voucher.getEndDate(),
                voucher.getCreatedReason());
    }
}