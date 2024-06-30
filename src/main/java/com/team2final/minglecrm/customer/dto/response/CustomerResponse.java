package com.team2final.minglecrm.customer.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.customer.domain.Customer;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomerResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String employeeName;
    private final String grade;
    private final String gender;
    private final LocalDate birth;

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getEmployee().getName(),
                customer.getGrade(),
                customer.getGender(),
                customer.getBirth()
        );
    }

    @QueryProjection
    public CustomerResponse(Long id, String name, String email, String phone, String employeeName, String grade, String gender, LocalDate birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.employeeName = employeeName;
        this.grade = grade;
        this.gender = gender;
        this.birth = birth;
    }
}
