package com.team2final.minglecrm.statistics.service.dish;

import com.team2final.minglecrm.statistics.domain.PurchaseDish;
import com.team2final.minglecrm.statistics.domain.repository.dish.PurchaseDishRepository;
import com.team2final.minglecrm.statistics.dto.response.dish.DishAmountResponse;
import com.team2final.minglecrm.statistics.dto.response.dish.StatisticsDishResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsDishService {

    private final PurchaseDishRepository purchaseDishRepository;

    // 판매된 상품(dish) 전체 조회
    public List<StatisticsDishResponse> getAllPurchaseDish(Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<StatisticsDishResponse> result = purchaseDishRepository.findAllBy(pageable).stream()
                .map(purchaseDish -> new StatisticsDishResponse(
                        purchaseDish.getId(),
                        purchaseDish.getPurchaseDate(),
                        purchaseDish.getName(),
                        purchaseDish.getQuantity(),
                        purchaseDish.getAmount()
                )).collect(Collectors.toList());
        return result;
    }

    // 직원은 기간을 설정하여 판매된 상품(dish) 수를 조회할 수 있다.
    public List<StatisticsDishResponse> getPurchaseDishByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<StatisticsDishResponse> result = purchaseDishRepository.findByPurchaseDateBetween(startDate, endDate, pageable).stream()
                .map(purchaseDish -> new StatisticsDishResponse(
                        purchaseDish.getId(),
                        purchaseDish.getPurchaseDate(),
                        purchaseDish.getName(),
                        purchaseDish.getQuantity(),
                        purchaseDish.getAmount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 특정 기간 판매된 음식 가격 총합
    public Long calculateTotalAmount(LocalDate startDate, LocalDate endDate) {
        return purchaseDishRepository.findTotalAmountByPurchaseDateBetween(startDate, endDate);
    }

    // 전날 판매된 음식 가격 총합
    public Long calculateTotalPriceYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Long result = purchaseDishRepository.findTotalAmountByPurchaseDate(yesterday) != null ? purchaseDishRepository.findTotalAmountByPurchaseDate(yesterday) : 0L;
        return result;
    }
}
