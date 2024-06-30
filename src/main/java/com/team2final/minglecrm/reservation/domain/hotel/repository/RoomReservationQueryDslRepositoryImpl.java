package com.team2final.minglecrm.reservation.domain.hotel.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.reservation.domain.hotel.QRoomReservation;
import com.team2final.minglecrm.reservation.dto.hotel.response.QRoomReservationStatisticsResponse;
import com.team2final.minglecrm.reservation.dto.hotel.response.RoomReservationStatisticsResponse;
import jakarta.persistence.EntityManager;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Repository
public class RoomReservationQueryDslRepositoryImpl implements RoomReservationQueryDslRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RoomReservationQueryDslRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public RoomReservationStatisticsResponse findRoomReservationStatistics(LocalDate startDate, LocalDate endDate) {

        QRoomReservation roomReservation = QRoomReservation.roomReservation;
        QCustomer customer = QCustomer.customer;

        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null && endDate != null) {
            builder.and(roomReservation.startDate.between(startDate, endDate));
        }

        NumberTemplate<Integer> age = Expressions.numberTemplate(Integer.class,
                "TIMESTAMPDIFF(YEAR, {0}, {1})", customer.birth, roomReservation.startDate);

        return queryFactory
                .select( new QRoomReservationStatisticsResponse(
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(20, 29).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(20, 29).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(30, 39).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(30, 39).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(40, 49).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(40, 49).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(50, 59).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.between(50, 59).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.notBetween(0, 59).and(customer.gender.eq("Male"))).then(1)
                                        .otherwise(0).sum().intValue()
                        ),
                        Expressions.asNumber(
                                new CaseBuilder()
                                        .when(age.notBetween(0, 59).and(customer.gender.eq("Female"))).then(1)
                                        .otherwise(0).sum().intValue()
                        )
                ))
                .from(roomReservation)
                .join(roomReservation.customer, customer)
                .where(builder)
                .fetchOne();

    }
}
