
package com.team2final.minglecrm.statistics.dto.response.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
//@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class DailyReservationResponse {

    private Long id;
    private Integer reservationYear;
    private Integer reservationMonth;
    private Integer reservationDay;
    private Long reservationCount;
    private LocalDate reservationDate;


    public DailyReservationResponse(Long id, Integer reservationYear, Integer reservationMonth, Integer reservationDay, Long reservationCount) {
        this.id = id;
        this.reservationYear = reservationYear;
        this.reservationMonth = reservationMonth;
        this.reservationDay = reservationDay;
        this.reservationCount = reservationCount;
        this.reservationDate = LocalDate.of(reservationYear, reservationMonth, reservationDay);
    }




}
