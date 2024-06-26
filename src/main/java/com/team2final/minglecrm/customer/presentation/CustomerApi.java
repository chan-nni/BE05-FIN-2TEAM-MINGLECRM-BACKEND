package com.team2final.minglecrm.customer.presentation;

import com.team2final.minglecrm.customer.dto.request.CustomerSearchCondition;
import com.team2final.minglecrm.customer.dto.response.CustomerResponse;
import com.team2final.minglecrm.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerApi {
    private final CustomerService customerService;

    // TODO : paging 처리 -> 컨텐츠 총 개수 뽑기
    @GetMapping()
    //    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(Pageable pageable) {
        Page<CustomerResponse> customers = customerService.getAllCustomer(pageable);
        return ResponseEntity.ok(customers);
    }

    // 검색
    @GetMapping("/search")
    public ResponseEntity<Page<CustomerResponse>> searchCustomers(
            Pageable pageable,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "gender", required = false) String gender
    ) {
        CustomerSearchCondition condition = new CustomerSearchCondition(name, grade, gender);
        Page<CustomerResponse> customers = customerService.search(pageable, condition);
        return ResponseEntity.ok(customers);
    }

}
