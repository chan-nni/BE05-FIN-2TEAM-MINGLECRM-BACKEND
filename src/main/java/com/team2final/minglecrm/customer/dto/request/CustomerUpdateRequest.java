package com.team2final.minglecrm.customer.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {

    private String name;
    private String grade;
    private String phone;
    private String address;
    private String employeeName;
    private String memo;
    private String gender;

}
