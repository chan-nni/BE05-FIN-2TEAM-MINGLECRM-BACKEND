package com.team2final.minglecrm.log.domain;


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
public class ViewLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String log;

    private LocalDateTime viewTime;


    @Builder
    public ViewLog(Long id, Customer customer, Employee employee, String log, LocalDateTime viewTime) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.log = log;
        this.viewTime = viewTime;
    }

}
