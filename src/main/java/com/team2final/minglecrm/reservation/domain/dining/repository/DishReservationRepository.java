package com.team2final.minglecrm.reservation.domain.dining.repository;

import com.team2final.minglecrm.reservation.domain.dining.DishReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishReservationRepository extends JpaRepository<DishReservation, Long> {
    List<DishReservation> findByCustomerId(Long customerId);

}
