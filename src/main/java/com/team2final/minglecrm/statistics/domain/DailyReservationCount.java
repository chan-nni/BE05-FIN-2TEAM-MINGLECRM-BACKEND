package com.team2final.minglecrm.statistics.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class DailyReservationCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer reservationYear;
    private Integer reservationMonth;
    private Integer reservationDay;
    private Long reservationCount;

    private LocalDate reservationDate;

    @Builder
    public DailyReservationCount(Long id, Integer reservationYear, Integer reservationMonth, Integer reservationDay, Long reservationCount) {
        this.id = id;
        this.reservationYear = reservationYear;
        this.reservationMonth = reservationMonth;
        this.reservationDay = reservationDay;
        this.reservationCount = reservationCount;
        this.reservationDate = LocalDate.of(reservationYear, reservationMonth, reservationDay);
    }
}
