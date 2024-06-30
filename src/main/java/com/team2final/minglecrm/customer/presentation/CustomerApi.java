package com.team2final.minglecrm.customer.presentation;

import com.team2final.minglecrm.customer.dto.request.CustomerSearchCondition;
import com.team2final.minglecrm.customer.dto.response.CustomerResponse;
import com.team2final.minglecrm.customer.service.CustomerService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerApi {
    private final CustomerService customerService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('MARKETER', 'MANAGER', 'CONSULTANT')")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(Pageable pageable) {
        Page<CustomerResponse> customers = customerService.getAllCustomer(pageable);
        return ResponseEntity.ok(customers);
    }

    // 검색
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('CONSULTANT', 'MANAGER','MARKETER')")
    public ResponseEntity<Page<CustomerResponse>> searchCustomers(
            Pageable pageable,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "email", required = false) String email
    ) {
        CustomerSearchCondition condition = new CustomerSearchCondition(name, grade, gender, email);
        Page<CustomerResponse> customers = customerService.search(pageable, condition);
        return ResponseEntity.ok(customers);
    }

}
