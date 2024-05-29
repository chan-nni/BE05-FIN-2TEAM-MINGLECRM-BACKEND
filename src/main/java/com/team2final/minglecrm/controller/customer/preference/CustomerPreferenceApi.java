package com.team2final.minglecrm.controller.customer.preference;

import com.team2final.minglecrm.controller.customer.preference.response.CustomerPreferenceResponse;
import com.team2final.minglecrm.controller.customer.preference.response.PreferenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team2final.minglecrm.service.customer.preference.CustomerPreferenceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CustomerPreferenceApi {

    private final CustomerPreferenceService customerPreferenceService;

    // 선호도 종류 조회
    @GetMapping("/preferences")
    private PreferenceResponse getCustomerPreferenceResponse() {
        customerPreferenceService.getCustomerPreferences();
        return null;
    }

    // 고객별 선호도 조회
    @GetMapping("/customers/{customerId}/preferences")
    private CustomerPreferenceResponse getCustomerPreferenceResponse(@PathVariable Long customerId) {
        return null;
    }


}