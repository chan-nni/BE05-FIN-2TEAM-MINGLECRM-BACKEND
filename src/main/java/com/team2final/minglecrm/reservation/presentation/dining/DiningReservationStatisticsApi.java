package com.team2final.minglecrm.reservation.presentation.dining;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.reservation.dto.dining.response.DishReservationStatisticsResponse;
import com.team2final.minglecrm.reservation.service.dining.DishReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiningReservationStatisticsApi {

    private final DishReservationService dishReservationService;

    @GetMapping("/api/dining/reservation/statistics")
    public ResultResponse<DishReservationStatisticsResponse> getDishReservationStatistics(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        DishReservationStatisticsResponse response = dishReservationService.getDishReservationStatistics(startDate, endDate);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
    }

}
