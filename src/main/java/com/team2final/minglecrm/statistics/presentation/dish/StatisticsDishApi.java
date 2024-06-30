package com.team2final.minglecrm.statistics.presentation.dish;

import com.team2final.minglecrm.statistics.domain.PurchaseDish;
import com.team2final.minglecrm.statistics.dto.response.dish.DishAmountResponse;
import com.team2final.minglecrm.statistics.dto.response.dish.StatisticsDishResponse;
import com.team2final.minglecrm.statistics.service.dish.StatisticsDishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic/dish")
public class StatisticsDishApi {

    private final StatisticsDishService statisticsDishService;

    // 판매된 상품(dish) 전체 조회
    @GetMapping
    public ResponseEntity<List<StatisticsDishResponse>> getAllPurchaseDish(Pageable pageable) {
        List<StatisticsDishResponse> result = statisticsDishService.getAllPurchaseDish(pageable);
        return ResponseEntity.ok(result);
    }

    // 직원은 기간을 설정하여 판매된 상품(dish) 수를 조회할 수 있다.
    @GetMapping("/purchase-dish-date")
    public ResponseEntity<List<StatisticsDishResponse>> getPurchaseDishByDateRange(@RequestParam(value = "start", required = false) LocalDate startDate,
                                                                                   @RequestParam(value = "end", required = false) LocalDate endDate,
                                                                                   Pageable pageable) {
        try {

            List<StatisticsDishResponse> result = statisticsDishService.getPurchaseDishByDateRange(startDate, endDate, pageable);
            if (endDate.isBefore(startDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "종료 날짜가 시작 날짜 보다 빠릅니다.");
            }
            return ResponseEntity.ok(result);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "날짜 형식이 옳바르지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "판매 상품을 조회할 수  없습니다.", e);
        }
    }

    @GetMapping("/total-price-yesterday")
    public ResponseEntity<DishAmountResponse> getTotalPriceYesterday() {
        Long totalPrice = statisticsDishService.calculateTotalPriceYesterday();
        DishAmountResponse result = new DishAmountResponse(totalPrice);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/total-amount")
    public ResponseEntity<DishAmountResponse> getTotalAmount(@RequestParam("start") LocalDate startDate,
                                                             @RequestParam("end") LocalDate endDate) {

        Long totalAmount = statisticsDishService.calculateTotalAmount(startDate, endDate);
        DishAmountResponse result = new DishAmountResponse(totalAmount);
        return ResponseEntity.ok(result);

    }
}
