package com.team2final.minglecrm.log.domain;

import com.team2final.minglecrm.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee employee;

    private String name;

    private String email;

    private String password;

    private String authority;

    private LocalDateTime requestDate;

    private Boolean isApproved;

    private LocalDateTime authorityChangeDate;

    private Boolean isDeleted;

    private LocalDateTime deleteDate;


}
