package com.team2final.minglecrm.reservation.domain.hotel;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.payment.domain.Payment;
import com.team2final.minglecrm.reservation.dto.hotel.request.UpdateRoomReservationRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hotel_room_id")
    private HotelRoom hotelRoom;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private LocalDateTime reservationDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long price;

    public void updateHotelReservationInfo(
            UpdateRoomReservationRequest updateRoomReservationRequest) {
        this.price = updateRoomReservationRequest.getPrice();
        this.reservationDate = updateRoomReservationRequest.getReservationDate();

        this.customer.updateCustomerReservationDetail(
                updateRoomReservationRequest.getMemo(),
                updateRoomReservationRequest.getName()
        );
    }

    public void deleteHotelReservation() {
        this.payment.cancelReservation(true);
    }
}
