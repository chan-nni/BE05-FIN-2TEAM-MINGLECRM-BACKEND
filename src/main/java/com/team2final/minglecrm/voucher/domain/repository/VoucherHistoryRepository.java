package com.team2final.minglecrm.voucher.domain.repository;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.voucher.domain.VoucherHistory;
import com.team2final.minglecrm.voucher.domain.status.VoucherStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface VoucherHistoryRepository extends JpaRepository<VoucherHistory, Long> {
    VoucherHistory findByVoucherId(Long voucherId);

    boolean existsByVoucherCode(String voucherCode);

    List<VoucherHistory> findAllByStatus(VoucherStatusType status);

    List<VoucherHistory> findAllByEmployeeStaffAndStatus(Employee employee, VoucherStatusType status);

    // customer
    VoucherHistory findByCustomerAndVoucherId(Customer customer, Long voucherId);

    // customer
    List<VoucherHistory> findAllByCustomer(Customer customer);

    List<VoucherHistory> findAllByEmployeeStaffAndStatusOrStatus(Employee employee, VoucherStatusType status1, VoucherStatusType status2);

    List<VoucherHistory> findVoucherHistoriesByEmployeeStaffAndStatusIn(Employee employeeStaff, List<VoucherStatusType> status);
}