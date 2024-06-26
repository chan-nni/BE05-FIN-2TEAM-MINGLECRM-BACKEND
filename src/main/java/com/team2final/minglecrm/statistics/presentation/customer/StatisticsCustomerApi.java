package com.team2final.minglecrm.statistics.presentation.customer;

import com.team2final.minglecrm.statistics.dto.response.customer.StatisticsCustomerResponse;
import com.team2final.minglecrm.statistics.dto.response.customer.VisitCustomerResponse;
import com.team2final.minglecrm.statistics.service.customer.StatisticsCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics/customers")
public class StatisticsCustomerApi {

    private final StatisticsCustomerService statisticsCustomerService;

    // 직원은 기간을 설정하여 고객 신규 유입자 수를 조회할 수 있다.
    @GetMapping("/new-customers")
    public List<StatisticsCustomerResponse> getNewCustomers(@RequestParam("start") String startDate,
                                                            @RequestParam("end") String endDate,
                                                            @PageableDefault(sort = "id") Pageable pageable) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            if (end.isBefore(start)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "종료 날짜가 시작 날짜 보다 빠릅니다.");
            }
            return statisticsCustomerService.findNewCustomers(start, end, pageable);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "날짜 형식이 옳바르지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "신규 고객 정보를 조회할 수 없습니다.", e);
        }
    }

    // 직원은 기간을 설정하여 방문 고객을 조회할 수 있다.
    @GetMapping("/visit-customers")
    public List<VisitCustomerResponse> getVisitCustomers(@RequestParam("start") String startDateStr,
                                                         @RequestParam("end") String endDateStr,
                                                         @PageableDefault(sort = "id") Pageable pageable) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            if (endDate.isBefore(startDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "종료 날짜가 시작 날짜 보다 빠릅니다.");
            }

            return statisticsCustomerService.findCustomersByReservationDateBetween(startDate, endDate, pageable);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "날짜 형식이 옳바르지 않습니다.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "고객 정보를 조회할 수 없습니다.", e);
        }
    }

    // 직원은 특정 방문 횟수 이상인 고객을 조회할 수 있다.
    @GetMapping("/visit-cnt-customers")
    public List<StatisticsCustomerResponse> getCustomersVisitCnt(@RequestParam("visitCnt") Integer visitCnt,
                                                                 @PageableDefault(sort = "id") Pageable pageable) {
        try {
            return statisticsCustomerService.findByVisitCntGreaterThan(visitCnt,pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "방문 횟수에 적합한 고객 정보를 조회할 수 없습니다.", e);
        }
    }

    // 직원은 특정 그룹 별 고객 재방문율을 조회할 수 있다.
    @GetMapping("/revisit-rate")
    public Map<String, Object> getRevisitRates() {
        Map<String, Object> response = new HashMap<>();
        double overallRevisitRate = statisticsCustomerService.calculateRevisitRate();
        Map<String, Double> revisitRateByGender = statisticsCustomerService.calculateRevisitRateByGender();
        Map<String, Double> revisitRateByGrade = statisticsCustomerService.calculateRevisitRateByGrade();

        response.put("overallRevisitRate", overallRevisitRate);
        response.put("revisitRateByGender", revisitRateByGender);
        response.put("revisitRateByGrade", revisitRateByGrade);

        return response;
    }
}
