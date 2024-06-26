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
public class MonthlyReservationCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer reservationYear;
    private Integer reservationMonth;
    private Long reservationCount;

    @Builder
    public MonthlyReservationCount(Integer reservationYear, Integer reservationMonth, Long reservationCount) {
        this.reservationYear = reservationYear;
        this.reservationMonth = reservationMonth;
        this.reservationCount = reservationCount;
    }
}
