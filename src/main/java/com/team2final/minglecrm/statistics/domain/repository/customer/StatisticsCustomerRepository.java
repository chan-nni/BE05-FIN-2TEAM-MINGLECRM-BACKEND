package com.team2final.minglecrm.statistics.domain.repository.customer;

import com.team2final.minglecrm.customer.domain.Customer;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatisticsCustomerRepository extends JpaRepository<Customer, Long> {

    // 직원은 기간을 설정하여 고객 신규 유입자 수를 조회할 수 있다.
    Page<Customer> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 직원은 기간을 설정하여 방문 고객을 조회할 수 있다.
    @Query("SELECT c FROM Customer c JOIN FETCH c.roomReservations r WHERE r.startDate <= :endDate AND r.endDate >= :startDate")
    Page<Customer> findCustomersByReservationDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 직원은 기간을 설정하여 방문 고객을 조회할 수 있다. - 페이징처리 x
    @Query("SELECT c FROM Customer c JOIN FETCH c.roomReservations r WHERE r.startDate <= :endDate AND r.endDate >= :startDate")
    List<Customer> findCustomersByReservationDateBetween(LocalDate startDate, LocalDate endDate);


    // 직원은 특정 방문 횟수 이상인 고객을 조회할 수 있다.
    @Query("SELECT c FROM Customer c WHERE c.visitCnt > :visitCnt")
    Page<Customer> findByVisitCntGreaterThan(@Param("visitCnt") Integer visitCnt, Pageable pageable);

    long countByIsDeletedFalse();
    long countByGender(String gender);
    long countByGrade(String graded);

}
