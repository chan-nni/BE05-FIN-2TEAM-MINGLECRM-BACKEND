package com.team2final.minglecrm.reservation.domain.dining;

import com.team2final.minglecrm.customer.domain.Customer;
import com.team2final.minglecrm.payment.domain.Payment;
import com.team2final.minglecrm.reservation.dto.dining.request.UpdateDiningReservationRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DishReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime reservationDate;

    private Integer visitorCount;
    private Long totalPrice;

    private LocalDateTime visitDate;

    private String restaurant;

    @OneToMany(mappedBy = "dishReservation")
    private List<DishReservationDetail> dishReservationDetails;

    public void updateDishReservation(UpdateDiningReservationRequest updateDiningReservationRequest) {

    }

    public void cancelDishReservation() {
        this.payment.cancelReservation(true);
    }


    @Builder
    public DishReservation(Long totalPrice, LocalDateTime reservationDate, Customer customer, Integer visitCount, String restaurant) {
        this.totalPrice = totalPrice;
        this.reservationDate = reservationDate;
        this.customer = customer;
        this.visitorCount = visitCount;
        this.restaurant = restaurant;
    }
}
