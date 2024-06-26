package com.team2final.minglecrm.reward.domain;

import com.team2final.minglecrm.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Long amount;

    @Builder
    public Reward(Customer customer, Long amount){
        this.customer = customer;
        this.amount = amount;
    }
}
