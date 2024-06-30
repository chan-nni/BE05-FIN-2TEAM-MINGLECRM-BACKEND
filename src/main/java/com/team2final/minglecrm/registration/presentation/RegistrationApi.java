package com.team2final.minglecrm.registration.presentation;

import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import com.team2final.minglecrm.registration.dto.request.RegistrationRequest;
import com.team2final.minglecrm.registration.dto.request.RegistrationSearchCondition;
import com.team2final.minglecrm.registration.dto.request.UpdateStatusRequest;
import com.team2final.minglecrm.registration.dto.response.RegistrationResponse;
import com.team2final.minglecrm.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/registers")
public class RegistrationApi {

    private final RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<Page<RegistrationResponse>> getPendingStatusEmployList(Pageable pageable) {
        Page<RegistrationResponse> registrationResponses = registrationService.getPendingStatusEmployList(pageable);
        return ResponseEntity.ok(registrationResponses);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<RegistrationResponse>> getAllStatusEmployList(Pageable pageable) {
        Page<RegistrationResponse> registrationResponses = registrationService.getAllStatusEmployList(pageable);
        return ResponseEntity.ok(registrationResponses);
    }

    @GetMapping("/pendingCount")
    public ResponseEntity<Map<String, Long>> getPendingCount() {
        Long count = registrationService.getPendingCount();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        System.out.println("count = " + count);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/updateStatus")
    public ResponseEntity<RegistrationResponse> updateEmployeeRequestStatus(
            @RequestParam String action,
            @RequestBody UpdateStatusRequest updateStatusRequest) {

        SignUpRequest signUpRequest = updateStatusRequest.getSignUpRequest();
        RegistrationRequest registrationRequest = updateStatusRequest.getRegistrationRequest();

        if ("approve".equalsIgnoreCase(action)) {
            registrationService.approvedEmployeeRequest(signUpRequest);
            return null;
        }

        if ("reject".equalsIgnoreCase(action)) {
            registrationService.rejectEmployeeRequest(signUpRequest);
            return null;
        }
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RegistrationResponse>> getAllEmployList(
            @ModelAttribute RegistrationSearchCondition condition,
            Pageable pageable) {

        Page<RegistrationResponse> registrationResponses = registrationService.searchRegistration(condition, pageable);
        return ResponseEntity.ok(registrationResponses);

    }


}
