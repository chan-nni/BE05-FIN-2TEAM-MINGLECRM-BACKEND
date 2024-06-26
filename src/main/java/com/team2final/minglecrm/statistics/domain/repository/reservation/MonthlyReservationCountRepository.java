package com.team2final.minglecrm.statistics.domain.repository.reservation;

import com.team2final.minglecrm.statistics.domain.MonthlyReservationCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyReservationCountRepository extends JpaRepository<MonthlyReservationCount, Long> {

    List<MonthlyReservationCount> findAllBy(Pageable pageable);
}
