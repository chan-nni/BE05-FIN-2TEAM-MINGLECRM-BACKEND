package com.team2final.minglecrm.entity.customer;


import com.team2final.minglecrm.controller.customer.request.CustomerUpdateRequest;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.entity.reward.Reward;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;

    private String grade;

    private String phone;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String memo;

    private String gender;

    private LocalDate birth;

    @OneToOne(mappedBy = "customer")
    private Reward reward;

    private Boolean isDeleted;

    public void updateCustomerDetail(CustomerUpdateRequest customerUpdateRequest) {
        this.name = customerUpdateRequest.getName();
        this.gender = customerUpdateRequest.getGender();
        this.memo = customerUpdateRequest.getMemo();
        this.phone = customerUpdateRequest.getPhone();
        this.address = customerUpdateRequest.getAddress();
    }

    public void deleteCustomer() {
        this.isDeleted = true;
    }
}
