package com.team2final.minglecrm.log.service.view;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.customer.domain.repository.CustomerRepository;
import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.log.domain.ViewLog;
import com.team2final.minglecrm.log.domain.repository.ViewLogRepository;
import com.team2final.minglecrm.log.domain.repository.queryDsl.ViewLogSearchRepository;
import com.team2final.minglecrm.log.dto.view.request.ViewLogSearchCondition;
import com.team2final.minglecrm.log.dto.view.response.ViewLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewLogService {

    private final ViewLogRepository viewLogRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ViewLogSearchRepository viewLogSearchRepository;

    public List<ViewLogResponse> findAllLogs() {

        List<ViewLog> viewLogs = viewLogRepository.findAll();

        return viewLogs.stream()
                .map(ViewLogResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createViewLog(Long customerId, String employeeEmail) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("고객을 찾을 수 없습니다."));

        Employee employee = employeeRepository.findByEmail(employeeEmail).orElseThrow(
                () -> new RuntimeException("직원을 찾을 수 없습니다."));

        ViewLog createdViewLog = ViewLog.builder()
                .customer(customer)
                .employee(employee)
                .viewTime(LocalDateTime.now())
                .build();

        viewLogRepository.save(createdViewLog);
    }

    public Page<ViewLogResponse> search(Pageable pageable, ViewLogSearchCondition condition) {
        pageable = pageable == null ? PageRequest.of(0, 5) : pageable;
        List<ViewLogResponse> search = viewLogSearchRepository.search(condition);
        return new PageImpl<>(search, pageable, search.size());
    }
}