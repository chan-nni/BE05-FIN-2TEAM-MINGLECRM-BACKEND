package com.team2final.minglecrm.voucher.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.voucher.domain.QVoucherHistory;
import com.team2final.minglecrm.voucher.domain.status.VoucherStatusType;
import com.team2final.minglecrm.voucher.dto.request.VoucherSearchCondition;
import com.team2final.minglecrm.voucher.dto.response.QVoucherHistoryResponse;
import com.team2final.minglecrm.voucher.dto.response.VoucherHistoryResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VoucherSearchRepository {

    private final JPAQueryFactory queryFactory;

    public VoucherSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<VoucherHistoryResponse> search(VoucherSearchCondition condition) {
        QVoucherHistory voucherHistory = QVoucherHistory.voucherHistory;

        return queryFactory
                .select(new QVoucherHistoryResponse(
                        voucherHistory.id,
                        voucherHistory.voucher.id,
                        voucherHistory.status,
                        voucherHistory.requestedDate,
                        voucherHistory.voucher.createdReason,
                        voucherHistory.confirmedDate,
                        voucherHistory.convertedDate,
                        voucherHistory.employeeStaff.id,
                        voucherHistory.employeeStaff.name,
                        voucherHistory.employeeManager.id,
                        voucherHistory.employeeManager.name,
                        voucherHistory.customer.id,
                        voucherHistory.customer.name,
                        voucherHistory.customer.email,
                        voucherHistory.voucher.amount,
                        voucherHistory.rejectedReason,
                        voucherHistory.voucherCode,
                        voucherHistory.issueOrCancelDate,
                        voucherHistory.customer.grade,
                        voucherHistory.voucher.startDate,
                        voucherHistory.voucher.endDate
                ))
                .from(voucherHistory)
                .leftJoin(voucherHistory.voucher)
                .leftJoin(voucherHistory.employeeStaff)
                .leftJoin(voucherHistory.employeeManager)
                .leftJoin(voucherHistory.customer)
                .where(
                        requestDateEq(condition.getRequestDate()),
                        createdReasonContains(condition.getCreatedReason()),
                        confirmDateEq(condition.getConfirmDate()),
                        conversionDateEq(condition.getConversionDate()),
                        creatorNameContains(condition.getCreatorName()),
                        confirmerIdEq(condition.getConfirmerId()),
                        confirmerNameContains(condition.getConfirmerName()),
                        customerNameContains(condition.getCustomerName()),
                        customerEmailContains(condition.getCustomerEmail()),
                        amountEq(condition.getAmount()),
                        rejectedReasonContains(condition.getRejectedReason()),
                        voucherCodeContains(condition.getVoucherCode()),
                        dateBetween(condition.getStartDate(), condition.getEndDate()),
                        gradeIn(condition.getCustomerGrades()),
                        statusIn(condition.getStatus()),
                        startDateEq(condition.getStartDate()),
                        endDateEq(condition.getEndDate())
                )
                .fetch();
    }

    public BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate != null ? QVoucherHistory.voucherHistory.voucher.startDate.eq(startDate) : null;
    }

    public BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate != null ? QVoucherHistory.voucherHistory.voucher.endDate.eq(endDate) : null;
    }

    public BooleanExpression statusIn(List<VoucherStatusType> statuses) {
        return statuses != null && !statuses.isEmpty() ? QVoucherHistory.voucherHistory.status.in(statuses) : null;
    }

    public BooleanExpression gradeIn(List<String> grades) {
        return grades != null && !grades.isEmpty() ? QVoucherHistory.voucherHistory.customer.grade.in(grades) : null;
    }

    private BooleanExpression requestDateEq(LocalDateTime requestDate) {
        return requestDate != null ? QVoucherHistory.voucherHistory.requestedDate.eq(requestDate) : null;
    }

    private BooleanExpression createdReasonContains(String createdReason) {
        return createdReason != null ? QVoucherHistory.voucherHistory.voucher.createdReason.containsIgnoreCase(createdReason) : null;
    }

    private BooleanExpression confirmDateEq(LocalDateTime confirmDate) {
        return confirmDate != null ? QVoucherHistory.voucherHistory.confirmedDate.eq(confirmDate) : null;
    }

    private BooleanExpression conversionDateEq(LocalDateTime conversionDate) {
        return conversionDate != null ? QVoucherHistory.voucherHistory.convertedDate.eq(conversionDate) : null;
    }

    private BooleanExpression creatorNameContains(String creatorName) {
        return creatorName != null ? QVoucherHistory.voucherHistory.employeeStaff.name.containsIgnoreCase(creatorName) : null;
    }

    private BooleanExpression confirmerIdEq(Long confirmerId) {
        return confirmerId != null ? QVoucherHistory.voucherHistory.employeeManager.id.eq(confirmerId) : null;
    }

    private BooleanExpression confirmerNameContains(String confirmerName) {
        return confirmerName != null ? QVoucherHistory.voucherHistory.employeeManager.name.containsIgnoreCase(confirmerName) : null;
    }

    private BooleanExpression customerNameContains(String customerName) {
        return customerName != null ? QVoucherHistory.voucherHistory.customer.name.containsIgnoreCase(customerName) : null;
    }

    private BooleanExpression customerEmailContains(String customerEmail) {
        return customerEmail != null ? QVoucherHistory.voucherHistory.customer.email.containsIgnoreCase(customerEmail) : null;
    }

    private BooleanExpression amountEq(Long amount) {
        return amount != null ? QVoucherHistory.voucherHistory.voucher.amount.eq(amount) : null;
    }

    private BooleanExpression rejectedReasonContains(String rejectedReason) {
        return rejectedReason != null ? QVoucherHistory.voucherHistory.rejectedReason.containsIgnoreCase(rejectedReason) : null;
    }

    private BooleanExpression voucherCodeContains(String voucherCode) {
        return voucherCode != null ? QVoucherHistory.voucherHistory.voucherCode.containsIgnoreCase(voucherCode) : null;
    }

    private BooleanExpression dateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return QVoucherHistory.voucherHistory.confirmedDate.between(startDate, endDate);
        } else if (startDate != null) {
            return QVoucherHistory.voucherHistory.confirmedDate.goe(startDate);
        } else if (endDate != null) {
            return QVoucherHistory.voucherHistory.confirmedDate.loe(endDate);
        } else {
            return null;
        }
    }
}
