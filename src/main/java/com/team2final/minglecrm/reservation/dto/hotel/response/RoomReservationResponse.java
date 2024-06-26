package com.team2final.minglecrm.reservation.dto.hotel.response;

import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class RoomReservationResponse {

    // 상태, 예약일, 고객명, 연락처
//    private final String state;
    private final LocalDateTime reservationDate;
    private final String name;
    private final String phoneNumber;

    // 상품명(호텔이름), 예약타입, 예약내용
    private final String hotelName;
    private final String reservationType;
//    private final String reservationMemo;


    // 담당자, 메모
//    private final String employeeName;
    private final String memo;

    public static RoomReservationResponse of(RoomReservation roomReservation) {
        return new RoomReservationResponse(
                roomReservation.getReservationDate(),
                roomReservation.getCustomer().getName(),
                roomReservation.getCustomer().getPhone(),
                roomReservation.getHotelRoom().getHotel(),
                roomReservation.getHotelRoom().getRoomType().name(),
                roomReservation.getCustomer().getMemo()
                );
    }


}
