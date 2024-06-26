package com.team2final.minglecrm.customer.service.preference;

import com.team2final.minglecrm.customer.domain.CustomerPreference;
import com.team2final.minglecrm.customer.domain.preference.CustomerPreferenceType;
import com.team2final.minglecrm.customer.domain.repository.CustomerPreferenceRepository;
import com.team2final.minglecrm.customer.dto.preference.request.CustomerPreferenceUpdateRequest;
import com.team2final.minglecrm.customer.dto.preference.response.CustomerPreferenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerPreferenceService {

    private final CustomerPreferenceRepository customerPreferenceRepository;

    public CustomerPreferenceType[] getAllCustomerPreferences() {
        return CustomerPreferenceType.values();
    }


    public CustomerPreferenceResponse getCustomerPreference(Long customerId) {
        CustomerPreference customerPreference = customerPreferenceRepository.findByCustomerId(customerId);
        return CustomerPreferenceResponse.of(customerPreference);
    }


    @Transactional
    public void updateCustomerPreference(Long customerId, CustomerPreferenceUpdateRequest customerPreferenceUpdateRequest) {
        CustomerPreference customerPreference = customerPreferenceRepository.findByCustomerId(customerId);
        customerPreference.updateCustomerPreference(customerPreferenceUpdateRequest);
    }
}
