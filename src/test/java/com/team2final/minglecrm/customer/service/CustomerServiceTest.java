package com.team2final.minglecrm.customer.service;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.customer.domain.repository.CustomerRepository;
import com.team2final.minglecrm.customer.domain.repository.CustomerSearchRepository;
import com.team2final.minglecrm.customer.dto.request.CustomerMemoCreateAndUpdateRequest;
import com.team2final.minglecrm.customer.dto.request.CustomerSearchCondition;
import com.team2final.minglecrm.customer.dto.request.CustomerUpdateRequest;
import com.team2final.minglecrm.customer.dto.response.CustomerResponse;
import com.team2final.minglecrm.customer.fixture.CustomerFixture;
import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.log.service.view.ViewLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ViewLogService viewLogService;

    @Mock
    private CustomerSearchRepository customerSearchRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("모든 고객을 조회한다.")
    public void testGetAllCustomer() {
        Pageable pageable = PageRequest.of(0, 5);
        Customer customer = CustomerFixture.createCustomer();
        Page<Customer> customerPage = new PageImpl<>(Collections.singletonList(customer));
        when(customerRepository.findAllCustomersWithEmployees(pageable)).thenReturn(customerPage);

        Page<CustomerResponse> result = customerService.getAllCustomer(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("dongwook", result.getContent().get(0).getName());
    }

    @Test
    @DisplayName("고객 정보를 업데이트 한다.")
    public void testUpdateCustomer() {
        Long customerId = 1L;
        Customer customer = mock(Customer.class);  // 모킹된 객체 사용
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest request = new CustomerUpdateRequest();
        // 직접 필드 설정
        setField(request, "name", "Jane Doe");
        setField(request, "grade", "Gold");
        setField(request, "phone", "987-654-3210");
        setField(request, "address", "456 Elm St");
        setField(request, "employeeName", "New Employee");
        setField(request, "memo", "Updated memo");
        setField(request, "gender", "Female");

        customerService.updateCustomer(customerId, request);

        verify(customer).updateCustomerDetail(request);
    }

    @Test
    @DisplayName("고객을 탈퇴시킨다.")
    public void testDeleteCustomer() {
        Long customerId = 1L;
        Customer customer = mock(Customer.class);  // 모킹된 객체 사용
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(customerId);

        verify(customer).deleteCustomer();
    }

    @Test
    @DisplayName("고객 메모를 수정, 삭제한다.")
    public void testMakeMemo() {
        Long customerId = 1L;
        Customer customer = mock(Customer.class);  // 모킹된 객체 사용
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CustomerMemoCreateAndUpdateRequest request = new CustomerMemoCreateAndUpdateRequest("New memo");

        customerService.makeMemo(customerId, request);

        verify(customer).createAndUpdateMemo(request);
    }

    @Test
    @DisplayName("고객을 다중 검색으로 조회한다.")
    public void testSearch() {
        Pageable pageable = PageRequest.of(0, 5);
        CustomerSearchCondition condition = new CustomerSearchCondition("dongwook", "VIP", "Male");
        Page<CustomerResponse> customerPage = new PageImpl<>(List.of());

        when(customerSearchRepository.search(condition, pageable)).thenReturn(customerPage);

        Page<CustomerResponse> result = customerService.search(pageable, condition);
        assertNotNull(result);
    }

    // 직접 필드 설정 메소드
    private void setField(Object targetObject, String fieldName, Object value) {
        try {
            Field field = targetObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(targetObject, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
