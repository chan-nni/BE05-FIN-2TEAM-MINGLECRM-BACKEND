package com.team2final.minglecrm.entity.reward;

import com.team2final.minglecrm.entity.customer.Customer;
import com.team2final.minglecrm.entity.employee.Employee;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoucherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    private LocalDateTime requestDate;

    private Boolean isAuth;

    private LocalDateTime authDate;

    private Boolean isConvertedYn;

    private LocalDateTime conversionDate;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id")
    private Employee employeeManager;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "staff_id")
    private Employee employeeStaff;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
