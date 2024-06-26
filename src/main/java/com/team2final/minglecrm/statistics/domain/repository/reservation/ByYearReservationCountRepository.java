package com.team2final.minglecrm.statistics.domain.repository.reservation;

import com.team2final.minglecrm.statistics.domain.ByYearReservationCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ByYearReservationCountRepository extends JpaRepository<ByYearReservationCount, Long> {

    List<ByYearReservationCount> findAllBy(Pageable pageable);
}
