package com.team2final.minglecrm.customer.fixture;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.reward.domain.Reward;

import java.time.LocalDate;

public class CustomerFixture {

    public static Customer createCustomer() {
        Employee employee = createEmployee();
        Reward reward = createReward();

        return Customer.builder()
                .id(1L)
                .name("dongwook")
                .grade("VIP")
                .email("john.doe@example.com")
                .phone("123-456-7890")
                .address("123 Main St")
                .employee(employee)
                .memo("Initial memo")
                .gender("Male")
                .birth(LocalDate.of(1990, 1, 1))
                .reward(reward)
                .isDeleted(false)
                .build();
    }

    private static Employee createEmployee() {
        return Employee.builder()
                .name("Employee Name")
                .email("employee@example.com")
                .password("password")
                .authority("ROLE_MANAGER")
                .isDeleted(false)
                .build();
    }

    private static Reward createReward() {
        return Reward.builder()
                .customer(null)
                .amount(1000L)
                .build();
    }


}
