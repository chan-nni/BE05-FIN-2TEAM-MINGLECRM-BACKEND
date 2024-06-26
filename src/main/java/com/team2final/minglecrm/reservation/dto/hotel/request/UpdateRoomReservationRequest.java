package com.team2final.minglecrm.reservation.dto.hotel.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateRoomReservationRequest {

    private String name;
    private Long price;
    private String memo;
    private LocalDateTime reservationDate;

}
