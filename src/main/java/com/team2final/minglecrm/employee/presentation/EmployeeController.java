package com.team2final.minglecrm.employee.presentation;

import com.team2final.minglecrm.employee.dto.request.SignUpEmailAuthRequest;
import com.team2final.minglecrm.employee.dto.request.SignUpEmailRequest;
import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import com.team2final.minglecrm.employee.dto.request.UpdateEmployeeRequest;
import com.team2final.minglecrm.employee.dto.response.AuthEmailCheckResponse;
import com.team2final.minglecrm.employee.dto.response.AuthEmailSendResponse;
import com.team2final.minglecrm.employee.dto.response.EmployeeInfoResponse;
import com.team2final.minglecrm.employee.dto.response.SignUpResponse;
import com.team2final.minglecrm.employee.service.EmployeeService;
import com.team2final.minglecrm.registration.service.RegistrationService;
import com.team2final.minglecrm.service.email.EmailAuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmailAuthService emailAuthService;
    private final RegistrationService registrationService;


    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest requestDTO) {
        System.out.println(">>>>>>>>>> signup , " + requestDTO);
//        SignUpResponse responseDTO = employeeService.signUp(requestDTO);
//        System.out.println(">>>>>>>>>> responseDTO , " + responseDTO);

        registrationService.sendRegistration(requestDTO);
        return null;

//        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/auth/signup/emailauth")
    public ResponseEntity<AuthEmailSendResponse> AuthEmailSend(@RequestBody SignUpEmailRequest signUpEmailRequest) throws MessagingException {

        try {
            emailAuthService.SendSignUpAuthEmail(signUpEmailRequest.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(new AuthEmailSendResponse("failed", false));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new AuthEmailSendResponse("secuccess", true));
    }

    @PostMapping("/api/v1/auth/authcheck")
    public ResponseEntity<AuthEmailCheckResponse> AuthEmailCheck(@RequestBody SignUpEmailAuthRequest request) {
        Boolean isCorrect = emailAuthService.AuthEmailCheck(request.getAuthCode(), request.getEmail());
        if (isCorrect) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AuthEmailCheckResponse("success", true));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new AuthEmailCheckResponse("failed", false));
    }

    @GetMapping("/api/v1/employee/profile")
    public ResponseEntity<EmployeeInfoResponse> getEmployeeInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        EmployeeInfoResponse employeeInfo = employeeService.getEmployeeInfo(userEmail);
        return new ResponseEntity<>(employeeInfo, HttpStatus.OK);
    }


    @PostMapping("/api/v1/employee-info")
    public void updateEmployeeInfo(@RequestBody UpdateEmployeeRequest updateEmployeeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        employeeService.updateEmployeeInfo(updateEmployeeRequest, userEmail);
    }

}
