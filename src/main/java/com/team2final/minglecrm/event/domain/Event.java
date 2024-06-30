package com.team2final.minglecrm.event.domain;

import com.team2final.minglecrm.employee.domain.Employee;
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
public class Event {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", updatable=false)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime sentDate;

    private Long sendCount;

//    private Long readCount;

    @Builder
    public Event(String title, String content, Employee employee, LocalDateTime sentDate, Long sendCount) {
        this.title = title;
        this.content = content;
        this.employee = employee;
        this.sentDate = sentDate;
        this.sendCount = sendCount;
//        this.readCount = 0L;
    }

//    public Long increaseReadCount() {
//        this.readCount += 1;
//        return readCount;
//    }

    @Transactional
    public void increaseSendCount() {
        this.sendCount += 1;
    }
}
