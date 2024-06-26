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
public class ByYearReservationCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer reservationYear;
    private Long reservationCount;

    @Builder
    public ByYearReservationCount(Integer reservationYear, Long reservationCount) {
        this.reservationYear = reservationYear;
        this.reservationCount = reservationCount;
    }
}
