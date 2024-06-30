package com.team2final.minglecrm.customer.dto.response;

import com.team2final.minglecrm.customer.domain.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Getter
@RequiredArgsConstructor
public class CustomerDetailResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String employeeName;
    private final String memo;
    private final String grade;
    private final String address;
    private final String gender;
    private final LocalDate birth;
    private final int age;

    public static CustomerDetailResponse of(Customer customer) {
        return new CustomerDetailResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getEmployee().getName(),
                customer.getMemo(),
                customer.getGrade(),
                customer.getAddress(),
                customer.getGender(),
                customer.getBirth(),
                calculateAge(customer.getBirth())
        );
    }

    private static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
