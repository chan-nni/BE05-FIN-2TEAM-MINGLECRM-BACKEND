package com.team2final.minglecrm.registration.domain;

import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String email;
    private String requestedRole;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String approvalManagerName;

    private Boolean isRejected;

    private LocalDateTime registrationRequestTime;

    private LocalDateTime approvalCompletionTime;

    private LocalDateTime rejectionTime;

    @Builder
    public Registration(String name, String password, String email, String requestedRole, RequestStatus status) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.requestedRole = requestedRole;
        this.status = status;
        this.registrationRequestTime = LocalDateTime.now();
    }

    public void approveChangeStatus(String userEmail) {
        this.status = RequestStatus.APPROVED;
        this.approvalManagerName = userEmail;
        this.isRejected = false;
        this.approvalCompletionTime = LocalDateTime.now();
    }

    public void rejectedChangeStatus(String userEmail) {
        this.status = RequestStatus.REJECTED;
        this.approvalManagerName = userEmail;
        this.isRejected = true;
        this.rejectionTime = LocalDateTime.now();
    }
}
