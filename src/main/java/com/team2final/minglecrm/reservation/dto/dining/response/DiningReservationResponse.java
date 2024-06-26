package com.team2final.minglecrm.reservation.dto.dining.response;

import com.team2final.minglecrm.reservation.domain.dining.Dish;
import com.team2final.minglecrm.reservation.domain.dining.DishReservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class DiningReservationResponse {
    private final Long id;
    // 상태, 총 가격, 예약 날짜, 방문 일자
    private final Long totalPrice;
    private final LocalDateTime reservationDate;
    private final LocalDateTime visitDate;

    // 식사 상품 이름, 식사 가격, 식사 상품 개수
    private final List<DishResponse> dishes;

    public static DiningReservationResponse of(DishReservation dishReservation, List<Dish> dishes) {
        List<DishResponse> dishResponses = dishes.stream()
                .map(DishResponse::of)
                .collect(Collectors.toList());

        return new DiningReservationResponse(
                dishReservation.getId(),
                dishReservation.getTotalPrice(),
                dishReservation.getReservationDate(),
                dishReservation.getVisitDate(),
                dishResponses
        );
    }



}
