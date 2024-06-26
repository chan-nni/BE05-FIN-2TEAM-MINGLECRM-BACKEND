package com.team2final.minglecrm.log.domain.repository.queryDsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.employee.domain.QEmployee;
import com.team2final.minglecrm.log.domain.QViewLog;
import com.team2final.minglecrm.log.dto.view.request.ViewLogSearchCondition;
import com.team2final.minglecrm.log.dto.view.response.QViewLogResponse;
import com.team2final.minglecrm.log.dto.view.response.ViewLogResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ViewLogSearchRepository {

    private final JPAQueryFactory queryFactory;

    public ViewLogSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ViewLogResponse> search(ViewLogSearchCondition condition) {
        QViewLog viewLog = QViewLog.viewLog;

        return queryFactory
                .select(new QViewLogResponse(
                        viewLog.id,
                        viewLog.employee.name,
                        viewLog.employee.email,
                        viewLog.employee.authority,
                        viewLog.customer.name,
                        viewLog.customer.email,
                        viewLog.customer.grade,
                        viewLog.viewTime
                ))
                .from(viewLog)
                .where(
                        customerNameEq(condition.getCustomerName()),
                        customerGradeEq(condition.getCustomerGrade()),
                        customerEmailEq(condition.getCustomerEmail()),
                        employeeNameEq(condition.getEmployeeName()),
                        employeeGradeEq(condition.getEmployeeGrade()),
                        employeeEmailEq(condition.getEmployeeEmail()),
                        withinDateRange(condition.getStartDate(), condition.getEndDate())
                )
                .fetch();
    }

    private BooleanExpression customerNameEq(String customerName) {
        return customerName != null ? QCustomer.customer.name.contains(customerName) : null;
    }

    private BooleanExpression customerEmailEq(String customerEmail) {
        return customerEmail != null ? QCustomer.customer.email.contains(customerEmail) : null;
    }

    private BooleanExpression customerGradeEq(String customerGrade) {
        return customerGrade != null ? QCustomer.customer.grade.eq(customerGrade) : null;
    }

    private BooleanExpression employeeNameEq(String employeeName) {
        return employeeName != null ? QEmployee.employee.name.contains(employeeName) : null;
    }

    private BooleanExpression employeeGradeEq(String employeeGrade) {
        return employeeGrade != null ? QEmployee.employee.authority.eq(employeeGrade) : null;
    }

    private BooleanExpression employeeEmailEq(String employeeEmail) {
        return employeeEmail != null ? QEmployee.employee.email.contains(employeeEmail) : null;
    }

    private BooleanExpression startDateAfter(LocalDateTime startDate) {
        if (startDate == null) {
            return null;
        }
        LocalDateTime startDateTime = startDate.toLocalDate().atStartOfDay();
        return QViewLog.viewLog.viewTime.after(startDateTime);
    }

    private BooleanExpression endDateBefore(LocalDateTime endDate) {
        if (endDate == null) {
            return null;
        }
        LocalDateTime endDateTime = endDate.toLocalDate().atTime(LocalTime.MAX);
        return QViewLog.viewLog.viewTime.before(endDateTime);
    }

    private BooleanExpression withinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        BooleanExpression startCondition = startDateAfter(startDate);
        BooleanExpression endCondition = endDateBefore(endDate);

        if (startCondition != null && endCondition != null) {
            return startCondition.and(endCondition);
        } else if (startCondition != null) {
            return startCondition;
        } else if (endCondition != null) {
            return endCondition;
        } else {
            return null;
        }
    }
}
