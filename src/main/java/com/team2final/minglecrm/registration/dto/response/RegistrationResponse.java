package com.team2final.minglecrm.registration.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.team2final.minglecrm.registration.domain.Registration;
import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegistrationResponse {

    private final String name;
    private final String email;
    private final String password;
    private final String requestedRole;
    private final RequestStatus status;
    private final LocalDateTime registrationRequestTime;

    @QueryProjection
    public RegistrationResponse(String name, String email, String password, String requestedRole, RequestStatus status, LocalDateTime registrationRequestTime) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.requestedRole = requestedRole;
        this.status = status;
        this.registrationRequestTime = registrationRequestTime;
    }

    public static RegistrationResponse from(Registration registration) {
        return new RegistrationResponse(
                registration.getName(),
                registration.getEmail(),
                registration.getPassword(),
                registration.getRequestedRole(),
                registration.getStatus(),
                registration.getRegistrationRequestTime()
        );
    }
}
