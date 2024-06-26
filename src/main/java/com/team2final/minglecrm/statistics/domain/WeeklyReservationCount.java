package com.team2final.minglecrm.statistics.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class WeeklyReservationCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer reservationYear;
    private Integer reservationWeek;
    private Long reservationCount;

    @Builder
    public WeeklyReservationCount(Integer reservationYear, Integer reservationWeek, Long reservationCount) {
        this.reservationYear = reservationYear;
        this.reservationWeek = reservationWeek;
        this.reservationCount = reservationCount;
    }
}
