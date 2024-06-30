package com.team2final.minglecrm.statistics.domain.repository.dish;

import com.team2final.minglecrm.statistics.domain.PurchaseDish;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseDishRepository extends JpaRepository<PurchaseDish, Long> {

    // 판매된 상품(dish) 전체 조회
    List<PurchaseDish> findAllBy(Pageable pageable);

    // 직원은 기간을 설정하여 판매된 상품(dish) 수를 조회할 수 있다.
    List<PurchaseDish> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 특정 기간의 판매 음식 가격 조회
    @Query("SELECT SUM(p.amount) FROM PurchaseDish p WHERE p.purchaseDate BETWEEN :startDate AND :endDate")
    Long findTotalAmountByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    // 전 날 판매된 음식의 총 가격 조회
    @Query("SELECT SUM(p.amount) FROM PurchaseDish p WHERE p.purchaseDate = :date")
    Long findTotalAmountByPurchaseDate(LocalDate date);
}
