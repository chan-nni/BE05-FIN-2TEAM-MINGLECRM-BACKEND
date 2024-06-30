package com.team2final.minglecrm.customer.presentation;

import com.team2final.minglecrm.customer.dto.request.CustomerMemoCreateAndUpdateRequest;
import com.team2final.minglecrm.customer.dto.request.CustomerUpdateRequest;
import com.team2final.minglecrm.customer.dto.response.CustomerDetailResponse;
import com.team2final.minglecrm.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}")
public class CustomerDetailApi {

    private final CustomerService customerService;

    // 고객 단건 조회
    @GetMapping()
    @PreAuthorize("hasAnyRole('CONSULTANT', 'MANAGER','MARKETER')")
    public ResponseEntity<CustomerDetailResponse> customerDetail(@PathVariable Long customerId) {
        CustomerDetailResponse customerResponse = customerService.findById(customerId);
        return ResponseEntity.ok(customerResponse);
    }

    // 고객 정보 수정
    @PatchMapping()
    @PreAuthorize("hasAnyRole('CONSULTANT', 'MANAGER','MARKETER')")
    public void updateCustomer(@PathVariable Long customerId,
                               @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }

    // 고객 삭제
    @DeleteMapping()
    @PreAuthorize("hasAnyRole('CONSULTANT', 'MANAGER','MARKETER')")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    // 고객 메모
    @PutMapping("/memo")
    @PreAuthorize("hasAnyRole('CONSULTANT', 'MANAGER','MARKETER')")
    public void createAndUpdateMemo(@PathVariable Long customerId, @RequestBody CustomerMemoCreateAndUpdateRequest memoCreateAndUpdateRequest) {
        customerService.makeMemo(customerId, memoCreateAndUpdateRequest);
    }

}
