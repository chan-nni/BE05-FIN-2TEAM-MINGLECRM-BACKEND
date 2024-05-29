package com.team2final.minglecrm.entity.inquiry;

import com.team2final.minglecrm.entity.customer.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime date;

    private String inquiryTitle;

    private String inquiryContent;

    private String type;

    private Boolean isReply;

    private Boolean isActionNeeded;

    private String actionContent;

    @Builder
    public Inquiry(Customer customer, LocalDateTime date, String inquiryTitle, String inquiryContent, String type, Boolean isReply, Boolean isActionNeeded, String actionContent) {
        this.customer = customer;
        this.date = date;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.isReply = isReply;
        this.type = type;
        this.isActionNeeded = isActionNeeded;
        this.actionContent = actionContent;
    }

}
