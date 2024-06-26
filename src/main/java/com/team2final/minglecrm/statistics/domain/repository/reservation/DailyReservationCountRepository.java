package com.team2final.minglecrm.statistics.domain.repository.reservation;

import com.team2final.minglecrm.statistics.domain.DailyReservationCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyReservationCountRepository extends JpaRepository<DailyReservationCount, Long> {

    List<DailyReservationCount> findAllBy(Pageable pageable);

    List<DailyReservationCount> findByReservationDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 페이징처리 x
    List<DailyReservationCount> findByReservationDateBetween(LocalDate startDate, LocalDate endDate);
}
