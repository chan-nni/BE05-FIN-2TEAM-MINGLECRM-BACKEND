package com.team2final.minglecrm.statistics.presentation.reservation;

import com.team2final.minglecrm.statistics.dto.response.reservation.ByYearReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.DailyReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.MonthlyReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.WeeklyReservationResponse;
import com.team2final.minglecrm.statistics.service.reservation.StatisticsReservationService;
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
@RequestMapping("/api/v1/statistic/reservation")
public class StatisticsReservationController {

    private final StatisticsReservationService statisticsReservationService;

    // // 연 별 예약 수 조회
    @GetMapping("/by-year-reservation-cnt")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<ByYearReservationResponse>> getByYearReservationCount(Pageable pageable) {
        List<ByYearReservationResponse> result = statisticsReservationService.getAllByYearReservation(pageable);
        return ResponseEntity.ok(result);
    }

    // // 월 별 예약 수 조회
    @GetMapping("/monthly-reservation-cnt")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<MonthlyReservationResponse>> getMonthlyReservationCount(Pageable pageable) {
        List<MonthlyReservationResponse> result = statisticsReservationService.getAllMonthlyReservation(pageable);
        return ResponseEntity.ok(result);
    }

    // // 월 별 예약 수 조회 - 페이징 처리 x
    @GetMapping("/monthly-reservation-cnt/all")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<MonthlyReservationResponse>> getAllMonthlyReservationCount() {
        List<MonthlyReservationResponse> result = statisticsReservationService.getAllMonthlyReservation();
        return ResponseEntity.ok(result);
    }


    // // 주 별 예약 수 조회
    @GetMapping("/weekly-reservation-cnt")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<WeeklyReservationResponse>> getWeeklyReservationCount(Pageable pageable) {
        List<WeeklyReservationResponse> result = statisticsReservationService.getAllWeeklyReservation(pageable);
        return ResponseEntity.ok(result);
    }

    // // 주 별 예약 수 조회 - 페이징처리 x
    @GetMapping("/weekly-reservation-cnt/all")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<WeeklyReservationResponse>> getWeeklyReservationCount() {
        List<WeeklyReservationResponse> result = statisticsReservationService.getAllWeeklyReservation();
        return ResponseEntity.ok(result);
    }

    // // 일 별 예약 수 조회
    @GetMapping("/daily-reservation-cnt")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<DailyReservationResponse>> getDailyReservationCount(Pageable pageable) {
        List<DailyReservationResponse> result = statisticsReservationService.getAllDailyReservation(pageable);
        return ResponseEntity.ok(result);
    }

    // // 일 별 예약 수 조회 - 페이징처리 x
    @GetMapping("/daily-reservation-cnt/all")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<DailyReservationResponse>> getDailyReservationCount() {
        List<DailyReservationResponse> result = statisticsReservationService.getAllDailyReservation();
        return ResponseEntity.ok(result);
    }

    // 기간 설정해서 예약 수 조회
    @GetMapping("/daily-reservation-cnt-date")
    public ResponseEntity<List<DailyReservationResponse>> getDailyReservationsByDateRange(@RequestParam("start") String startDate,
                                                                                          @RequestParam("end") String endDate,
                                                                                          Pageable pageable) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            List<DailyReservationResponse> result = statisticsReservationService.getDailyReservationByDateRange(start, end, pageable);

            if (end.isBefore(start)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "종료 날짜가 시작 날짜 보다 빠릅니다.");
            }
            return ResponseEntity.ok(result);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "날짜 형식이 옳바르지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "예약 수를 조회할 수 없습니다.", e);
        }
    }

    // 기간 설정해서 예약 수 조회
    @GetMapping("/daily-reservation-cnt-date/all")
    public ResponseEntity<List<DailyReservationResponse>> getDailyReservationsByDateRange(@RequestParam("start") String startDate,
                                                                                          @RequestParam("end") String endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            List<DailyReservationResponse> result = statisticsReservationService.getDailyReservationByDateRange(start, end);

            if (end.isBefore(start)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "종료 날짜가 시작 날짜 보다 빠릅니다.");
            }
            return ResponseEntity.ok(result);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "날짜 형식이 옳바르지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "예약 수를 조회할 수 없습니다.", e);
        }
    }
}
