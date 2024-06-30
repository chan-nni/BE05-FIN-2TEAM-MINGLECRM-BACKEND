package com.team2final.minglecrm.reservation.domain.dining.repository;

import com.team2final.minglecrm.reservation.dto.dining.response.DishReservationStatisticsResponse;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DishReservationQueryDslRepository {
    DishReservationStatisticsResponse findDishReservationStatistics(LocalDate startDate, LocalDate endDate);
}
