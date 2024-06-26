package com.team2final.minglecrm.log.dto.view.response;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.log.domain.ViewLog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ViewLogResponse {

    private final Long id;

    private final String EmployeeName;

    private final String EmployeeEmail;

    private final String EmployeeGrade;

    private final String customerName;

    private final String customerEmail;

    private final String customerGrade;

    private final LocalDateTime viewTime;


    public static ViewLogResponse from(ViewLog viewLog) {
        return new ViewLogResponse(
                viewLog.getId(),
                viewLog.getEmployee().getName(),
                viewLog.getEmployee().getEmail(),
                viewLog.getEmployee().getAuthority(),
                viewLog.getCustomer().getName(),
                viewLog.getCustomer().getEmail(),
                viewLog.getCustomer().getGrade(),
                viewLog.getViewTime()
        );
    }

    @QueryProjection
    public ViewLogResponse(Long id, String employeeName, String employeeEmail, String employeeGrade, String customerName, String customerEmail, String customerGrade, LocalDateTime viewTime) {
        this.id = id;
        EmployeeName = employeeName;
        EmployeeEmail = employeeEmail;
        EmployeeGrade = employeeGrade;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerGrade = customerGrade;
        this.viewTime = viewTime;
    }
}
