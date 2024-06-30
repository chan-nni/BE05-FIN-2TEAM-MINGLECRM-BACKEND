package com.team2final.minglecrm.reservation.domain.dining.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.reservation.domain.dining.QDishReservation;
import com.team2final.minglecrm.reservation.domain.hotel.QRoomReservation;
import com.team2final.minglecrm.reservation.dto.dining.response.DishReservationStatisticsResponse;
import com.team2final.minglecrm.reservation.dto.dining.response.QDishReservationStatisticsResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class DishReservationQueryDslRepositoryImpl implements DishReservationQueryDslRepository {


    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DishReservationQueryDslRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public DishReservationStatisticsResponse findDishReservationStatistics(LocalDate startDate, LocalDate endDate) {

        QDishReservation dishReservation = QDishReservation.dishReservation;
        QCustomer customer = QCustomer.customer;

        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null && endDate != null) {
            builder.and(dishReservation.visitDate.between(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)));
        }

        NumberTemplate<Integer> age = Expressions.numberTemplate(Integer.class,
                "TIMESTAMPDIFF(YEAR, {0}, {1})", customer.birth, dishReservation.visitDate);

        return queryFactory
                .select(new QDishReservationStatisticsResponse(
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
                .from(dishReservation)
                .join(dishReservation.customer, customer)
                .where(builder)
                .fetchOne();
    }
}
