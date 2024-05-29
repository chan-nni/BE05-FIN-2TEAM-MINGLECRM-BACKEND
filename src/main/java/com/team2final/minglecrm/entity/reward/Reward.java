package com.team2final.minglecrm.entity.reward;

import com.team2final.minglecrm.entity.customer.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static jakarta.persistence.FetchType.LAZY;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @OneToOne (fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Long amount;
}
