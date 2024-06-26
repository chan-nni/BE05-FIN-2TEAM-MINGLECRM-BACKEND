package com.team2final.minglecrm.reservation.domain.hotel.repository;

import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    List<RoomReservation> findByCustomerId(Long customerId);

}
