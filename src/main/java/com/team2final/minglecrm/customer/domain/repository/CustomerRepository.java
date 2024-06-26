package com.team2final.minglecrm.customer.domain.repository;

import com.team2final.minglecrm.customer.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    List<Customer> findByEmployeeName(String employeeName);

    List<Customer> findByGrade(String grade);

    Page<Customer> findAllBy(Pageable pageable);

    @Query("SELECT c FROM Customer c JOIN FETCH c.employee")
    Page<Customer> findAllCustomersWithEmployees(Pageable pageable);
}
