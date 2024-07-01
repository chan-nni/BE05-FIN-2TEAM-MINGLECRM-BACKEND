package com.team2final.minglecrm.inquiry.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team2final.minglecrm.customer.domain.Customer;
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
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime date;

    private String inquiryTitle;

    private String inquiryContent;

    private String type;

    private Boolean isReply;

    @Builder
    public Inquiry(Long id, Customer customer, LocalDateTime date, String inquiryTitle, String inquiryContent, String type, Boolean isReply, Boolean isActionNeeded, String actionContent) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.isReply = isReply;
        this.type = type;
    }

    public void changeIsReply() {
        this.isReply = true;
    }

}
