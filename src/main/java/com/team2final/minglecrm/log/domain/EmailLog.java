package com.team2final.minglecrm.log.domain;


import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.event.domain.Event;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailLog {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Boolean isOpened;

    private LocalDateTime openedTime;

    @Builder
    public EmailLog(Event event, Customer customer) {
        this.event = event;
        this.customer = customer;
        this.isOpened = false;
        this.openedTime = null;
    }

    @Transactional
    public LocalDateTime open() {
        this.isOpened = true;
        this.openedTime = LocalDateTime.now();

        return this.openedTime;
    }

}
