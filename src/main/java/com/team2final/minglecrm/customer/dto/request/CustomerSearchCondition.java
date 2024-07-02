package com.team2final.minglecrm.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerSearchCondition {

    private String customerName;

    private String grade;

    private String gender;

    private String email;

    private String ageGroup;
}
