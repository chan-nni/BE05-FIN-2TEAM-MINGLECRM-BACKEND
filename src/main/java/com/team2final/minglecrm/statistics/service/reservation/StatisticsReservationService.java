package com.team2final.minglecrm.statistics.service.reservation;

import com.team2final.minglecrm.statistics.domain.repository.reservation.*;
import com.team2final.minglecrm.statistics.dto.response.reservation.ByYearReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.DailyReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.MonthlyReservationResponse;
import com.team2final.minglecrm.statistics.dto.response.reservation.WeeklyReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsReservationService {

    private final StatisticsRoomReservationRepository statisticsRoomReservationRepository;

    private final ByYearReservationCountRepository byYearReservationCountRepository;
    private final MonthlyReservationCountRepository monthlyReservationCountRepository;
    private final WeeklyReservationCountRepository weeklyReservationCountRepository;
    private final DailyReservationCountRepository dailyReservationCountRepository;

    // 연 별 예약 수 조회
    public List<ByYearReservationResponse> getAllByYearReservation(Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<ByYearReservationResponse> result = byYearReservationCountRepository.findAllBy(pageable).stream()
                .map(byYearReservationCount -> new ByYearReservationResponse(
                        byYearReservationCount.getId(),
                        byYearReservationCount.getReservationYear(),
                        byYearReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 월 별 예약 수 조회
    public List<MonthlyReservationResponse> getAllMonthlyReservation(Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<MonthlyReservationResponse> result = monthlyReservationCountRepository.findAllBy(pageable).stream()
                .map(monthlyReservationCount -> new MonthlyReservationResponse(
                        monthlyReservationCount.getId(),
                        monthlyReservationCount.getReservationYear(),
                        monthlyReservationCount.getReservationMonth(),
                        monthlyReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 월 별 예약 수 조회 - 페이징처리 X
    public List<MonthlyReservationResponse> getAllMonthlyReservation() {
        List<MonthlyReservationResponse> result = monthlyReservationCountRepository.findAll().stream()
                .map(monthlyReservationCount -> new MonthlyReservationResponse(
                        monthlyReservationCount.getId(),
                        monthlyReservationCount.getReservationYear(),
                        monthlyReservationCount.getReservationMonth(),
                        monthlyReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 주 별 예약 수 조회
    public List<WeeklyReservationResponse> getAllWeeklyReservation(Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<WeeklyReservationResponse> result = weeklyReservationCountRepository.findAllBy(pageable).stream()
                .map(weeklyReservationCount -> new WeeklyReservationResponse(
                        weeklyReservationCount.getId(),
                        weeklyReservationCount.getReservationYear(),
                        weeklyReservationCount.getReservationWeek(),
                        weeklyReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 주 별 예약 수 조회 페이징처리 x
    public List<WeeklyReservationResponse> getAllWeeklyReservation() {
        List<WeeklyReservationResponse> result = weeklyReservationCountRepository.findAll().stream()
                .map(weeklyReservationCount -> new WeeklyReservationResponse(
                        weeklyReservationCount.getId(),
                        weeklyReservationCount.getReservationYear(),
                        weeklyReservationCount.getReservationWeek(),
                        weeklyReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 일 별 예약 수 조회
    public List<DailyReservationResponse> getAllDailyReservation(Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<DailyReservationResponse> result = dailyReservationCountRepository.findAllBy(pageable).stream()
                .map(dailyReservationCount -> new DailyReservationResponse(
                        dailyReservationCount.getId(),
                        dailyReservationCount.getReservationYear(),
                        dailyReservationCount.getReservationMonth(),
                        dailyReservationCount.getReservationDay(),
                        dailyReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 일 별 예약 수 조회 - 페이징 처리 x
    public List<DailyReservationResponse> getAllDailyReservation() {
        List<DailyReservationResponse> result = dailyReservationCountRepository.findAll().stream()
                .map(dailyReservationCount -> new DailyReservationResponse(
                        dailyReservationCount.getId(),
                        dailyReservationCount.getReservationYear(),
                        dailyReservationCount.getReservationMonth(),
                        dailyReservationCount.getReservationDay(),
                        dailyReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 기간 설정해서 예약 수 조회
    public List<DailyReservationResponse> getDailyReservationByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        pageable = pageable == null ? PageRequest.of(0, 3) : pageable;
        List<DailyReservationResponse> result = dailyReservationCountRepository.findByReservationDateBetween(startDate, endDate, pageable).stream()
                .map(dailyReservationCount -> new DailyReservationResponse(
                        dailyReservationCount.getId(),
                        dailyReservationCount.getReservationYear(),
                        dailyReservationCount.getReservationMonth(),
                        dailyReservationCount.getReservationDay(),
                        dailyReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 기간 설정해서 예약 수 조회 - 페이징 처리 x
    public List<DailyReservationResponse> getDailyReservationByDateRange(LocalDate startDate, LocalDate endDate) {
        List<DailyReservationResponse> result = dailyReservationCountRepository.findByReservationDateBetween(startDate, endDate).stream()
                .map(dailyReservationCount -> new DailyReservationResponse(
                        dailyReservationCount.getId(),
                        dailyReservationCount.getReservationYear(),
                        dailyReservationCount.getReservationMonth(),
                        dailyReservationCount.getReservationDay(),
                        dailyReservationCount.getReservationCount()
                ))
                .collect(Collectors.toList());
        return result;
    }


}
