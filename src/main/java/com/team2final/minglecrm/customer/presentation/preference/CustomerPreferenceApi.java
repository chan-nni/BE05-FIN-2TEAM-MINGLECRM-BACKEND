package com.team2final.minglecrm.customer.presentation.preference;

import com.team2final.minglecrm.customer.domain.preference.CustomerPreferenceType;
import com.team2final.minglecrm.customer.service.preference.CustomerPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/preferences")
public class CustomerPreferenceApi {

    private final CustomerPreferenceService customerPreferenceService;

    // 선호도 목록 조회
    @GetMapping
    public ResponseEntity<CustomerPreferenceType[]> getCustomerPreferences() {
        CustomerPreferenceType[] preferenceResponses = customerPreferenceService.getAllCustomerPreferences();
        return ResponseEntity.ok(preferenceResponses);
    }


}
