package com.team2final.minglecrm.statistics.presentation.dish;

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
    public ResponseEntity<List<StatisticsDishResponse>> getPurchaseDishByDateRange(@RequestParam("start") String startDate,
                                                                   @RequestParam("end") String endDate,
                                                                   Pageable pageable) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            List<StatisticsDishResponse> result = statisticsDishService.getPurchaseDishByDateRange(start, end, pageable);
            if (end.isBefore(start)) {
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
}
