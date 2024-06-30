package com.team2final.minglecrm.employee.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class SignUpRequest {

    private String email;
    private String name;
    private String password;
    private String authority;

    @Builder
    public SignUpRequest(String email, String name, String password, String authority) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.authority = authority;
    }
}
