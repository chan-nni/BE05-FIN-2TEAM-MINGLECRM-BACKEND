package com.team2final.minglecrm.registration.dto.request;

import com.team2final.minglecrm.registration.domain.type.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RegistrationSearchCondition {

    private String name;
    private String requestedRole;
    private RequestStatus status;


}
