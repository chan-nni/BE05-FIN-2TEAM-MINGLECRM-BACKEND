package com.team2final.minglecrm.reservation.presentation.hotel;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.reservation.dto.hotel.response.RoomReservationStatisticsResponse;
import com.team2final.minglecrm.reservation.service.hotel.HotelReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class RoomReservationApi {

    private final HotelReservationService hotelReservationService;

    @GetMapping("/api/room/reservation/statistics")
    public ResultResponse<RoomReservationStatisticsResponse> getRoomReservationStatistics(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
            )
    {
        RoomReservationStatisticsResponse response = hotelReservationService.getRoomReservationStatistics(startDate, endDate);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
    }
}
