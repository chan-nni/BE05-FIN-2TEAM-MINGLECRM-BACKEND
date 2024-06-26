package com.team2final.minglecrm.inquiry.domain;

import com.team2final.minglecrm.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    private String reply;

    private LocalDateTime date;

    @Builder
    public InquiryReply(Long id, Employee employee, Inquiry inquiry, String reply, LocalDateTime date) {
        this.id = id;
        this.employee = employee;
        this.inquiry = inquiry;
        this.reply = reply;
        this.date = date;
    }

    public void updateReply(String updatedReply, LocalDateTime date, Employee employee){
        this.reply = updatedReply;
        this.date = date;
        this.employee = employee;
    }

}
