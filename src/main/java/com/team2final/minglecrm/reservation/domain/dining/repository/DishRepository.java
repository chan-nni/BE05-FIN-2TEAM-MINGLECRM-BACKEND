package com.team2final.minglecrm.reservation.domain.dining.repository;

import com.team2final.minglecrm.reservation.domain.dining.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
//    List<Dish> findByDishReservationId(Long reservationId);
    List<Dish> findByDishReservationsId(Long reservationId);

}
