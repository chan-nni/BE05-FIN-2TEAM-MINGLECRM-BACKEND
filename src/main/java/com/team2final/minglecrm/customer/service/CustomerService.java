package com.team2final.minglecrm.customer.service;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.customer.domain.repository.CustomerRepository;
import com.team2final.minglecrm.customer.domain.repository.CustomerSearchRepository;
import com.team2final.minglecrm.customer.dto.request.CustomerMemoCreateAndUpdateRequest;
import com.team2final.minglecrm.customer.dto.request.CustomerSearchCondition;
import com.team2final.minglecrm.customer.dto.request.CustomerUpdateRequest;
import com.team2final.minglecrm.customer.dto.response.CustomerDetailResponse;
import com.team2final.minglecrm.customer.dto.response.CustomerResponse;
import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.log.service.view.ViewLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ViewLogService viewLogService;
    private final CustomerSearchRepository customerSearchRepository;


    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAllCustomer(Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 5) : pageable;
        return customerRepository.findAllCustomersWithEmployees(pageable).map(customer ->
                new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmployee().getName(),
                        customer.getGrade(),
                        customer.getGender(),
                        customer.getBirth()
                )
        );
    }

    public CustomerDetailResponse findById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        if (customer.getIsDeleted()) {
            throw new RuntimeException("없슴둥");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        viewLogService.createViewLog(customerId, userEmail);
        

        return CustomerDetailResponse.of(customer);
    }

    public void updateCustomer(Long customerId, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.updateCustomerDetail(customerUpdateRequest);

    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.deleteCustomer();
    }

    @Transactional
    public void makeMemo(Long customerId, CustomerMemoCreateAndUpdateRequest memoCreateAndUpdateRequest) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.createAndUpdateMemo(memoCreateAndUpdateRequest);
    }

    public Page<CustomerResponse> search(Pageable pageable, CustomerSearchCondition condition) {
        pageable = pageable == null ? PageRequest.of(0, 5) : pageable;
        return customerSearchRepository.search(condition, pageable);
    }

}