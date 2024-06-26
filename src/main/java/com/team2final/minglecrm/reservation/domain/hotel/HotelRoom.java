package com.team2final.minglecrm.reservation.domain.hotel;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotel;

    private String address;

    private Integer roomNumber;

    @Enumerated(value = EnumType.STRING)
    private RoomType roomType;

    private String roomState;
}
