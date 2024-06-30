package com.team2final.minglecrm.registration.dto.request;

import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateStatusRequest {

    private SignUpRequest signUpRequest;

    private RegistrationRequest registrationRequest;

    @Builder
    public UpdateStatusRequest(SignUpRequest signUpRequest, RegistrationRequest registrationRequest) {
        this.signUpRequest = signUpRequest;
        this.registrationRequest = registrationRequest;
    }
}
