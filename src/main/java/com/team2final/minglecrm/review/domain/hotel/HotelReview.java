package com.team2final.minglecrm.review.domain.hotel;


import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@NamedEntityGraph(name = "HotelReview.withRoomReservationAndCustomer",
        attributeNodes = {
                @NamedAttributeNode("roomReservation"),
                @NamedAttributeNode("customer")
        })
public class HotelReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double kindnessRating;
    private Double cleanlinessRating;
    private Double convenienceRating;
    private Double locationRating;
    private String comment;
    private LocalDateTime createdTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "room_reservation_id")
    private RoomReservation roomReservation;



}
