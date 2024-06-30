package com.team2final.minglecrm.reservation.domain.hotel.repository;

import com.team2final.minglecrm.reservation.dto.hotel.response.RoomReservationStatisticsResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface RoomReservationQueryDslRepository {

    RoomReservationStatisticsResponse findRoomReservationStatistics(LocalDate startDate, LocalDate endDate);
}
