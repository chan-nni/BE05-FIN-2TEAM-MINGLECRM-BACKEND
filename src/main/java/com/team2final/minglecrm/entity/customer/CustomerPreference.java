package com.team2final.minglecrm.entity.customer;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Boolean isSmoking;
    private String purpose;
    private Boolean isPet;
    private LocalDateTime preferredCheckinTime;
    private LocalDateTime preferredCheckoutTime;
    private String dietaryRestrictions;
    private String referer;
    private String interest;
    private Boolean isBreakfastPreferred;

}

