package com.team2final.minglecrm.customer.domain;

import com.team2final.minglecrm.customer.dto.request.CustomerMemoCreateAndUpdateRequest;
import com.team2final.minglecrm.customer.dto.request.CustomerUpdateRequest;
import com.team2final.minglecrm.customer.fixture.CustomerFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = CustomerFixture.createCustomer();
    }

    @Test
    @DisplayName("고객 정보를 업데이트한다.")
    public void testUpdateCustomerDetail() {
        CustomerUpdateRequest request = new CustomerUpdateRequest("Jane Doe", "VIP", "987-654-3210", "456 Elm St", "Employee Name", "Updated memo", "Female");

        customer.updateCustomerDetail(request);

        assertEquals("Jane Doe", customer.getName());
        assertEquals("VIP", customer.getGrade());
        assertEquals("987-654-3210", customer.getPhone());
        assertEquals("456 Elm St", customer.getAddress());
        assertEquals("Employee Name", customer.getEmployee().getName());
        assertEquals("Updated memo", customer.getMemo());
        assertEquals("Female", customer.getGender());
    }

    @Test
    @DisplayName("고객 정보를 삭제한다.")
    public void testDeleteCustomer() {
        customer.deleteCustomer();
        assertTrue(customer.getIsDeleted());
    }

    @Test
    @DisplayName("고객 방 정보 에약을 변경한다.")
    public void testUpdateCustomerReservationDetail() {
        customer.updateCustomerReservationDetail("New reservation memo", "John Smith");

        assertEquals("New reservation memo", customer.getMemo());
        assertEquals("John Smith", customer.getName());
    }

    @Test
    @DisplayName("고객 이메일을 변경한다.")
    public void testSetEmail() {
        customer.setEmail("new.email@example.com");

        assertEquals("new.email@example.com", customer.getEmail());
    }

    @Test
    @DisplayName("고객 메모를 생성하고 업데이트한다.")
    public void testCreateAndUpdateMemo() {
        CustomerMemoCreateAndUpdateRequest request = new CustomerMemoCreateAndUpdateRequest("Updated memo content");

        customer.createAndUpdateMemo(request);

        assertEquals("Updated memo content", customer.getMemo());
    }
}
