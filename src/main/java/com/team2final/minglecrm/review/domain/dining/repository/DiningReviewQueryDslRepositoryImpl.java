package com.team2final.minglecrm.review.domain.dining.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.ai.dto.vo.DiningReviewForSummary;
import com.team2final.minglecrm.ai.dto.vo.QDiningReviewForSummary;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.reservation.domain.dining.QDishReservation;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewResponse;
import com.team2final.minglecrm.reservation.dto.dining.response.QDiningReviewConditionSearchResponse;
import com.team2final.minglecrm.review.domain.dining.QDiningReview;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewConditionSearchForSummaryResponse;
import com.team2final.minglecrm.review.dto.dining.response.QDiningReviewConditionSearchForSummaryResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

public class DiningReviewQueryDslRepositoryImpl implements DiningReviewQueryDslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DiningReviewQueryDslRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<DiningReviewConditionSearchResponse> searchByExpression(DiningReviewConditionSearchRequest condition, Pageable pageable) {

        QDiningReview diningReview = QDiningReview.diningReview;
        QCustomer customer = QCustomer.customer;
        QDishReservation dishReservation = QDishReservation.dishReservation;

        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getCustomerName() != null) {
            builder.and(customer.name.eq(condition.getCustomerName()));
        }
        if (condition.getRestaurant() != null) {
            builder.and(dishReservation.restaurant.eq(condition.getRestaurant()));
        }
        if (condition.getStartDate() != null && condition.getEndDate() != null) {
            builder.and(diningReview.createdDate.between(condition.getStartDate(), condition.getEndDate()));
        }
        List<DiningReviewConditionSearchResponse> response = queryFactory
                .select(new QDiningReviewConditionSearchResponse(
                        customer.name,
                        diningReview.kindnessRating,
                        diningReview.tasteRating,
                        diningReview.cleanlinessRating,
                        diningReview.atmosphereRating,
                        diningReview.review,
                        diningReview.createdDate,
                        dishReservation.restaurant
                ))
                .from(diningReview)
                .join(diningReview.customer, customer)
                .join(diningReview.dishReservation, dishReservation)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(response, pageable, response.size());
    }

    public List<DiningReviewForSummary> findAllByStartDateCondition(LocalDateTime startDate) {
        QDiningReview diningReview = QDiningReview.diningReview;
        return queryFactory
                .select(new QDiningReviewForSummary(
                        diningReview.review
                ))
                .from(diningReview)
                .where(diningReview.createdDate.after(startDate))
                .fetch();
    }

    @Override
    public List<DiningReviewConditionSearchForSummaryResponse> findDiningReviewsByCondition(DiningReviewConditionSearchRequest condition) {
        QDiningReview diningReview = QDiningReview.diningReview;
        QDishReservation dishReservation = QDishReservation.dishReservation;
        BooleanBuilder builder = new BooleanBuilder();

        if(condition.getRestaurant() != null) {
            builder.and(dishReservation.restaurant.eq(condition.getRestaurant()));
        }

        if (condition.getStartDate() != null && condition.getEndDate() != null) {
            builder.and(diningReview.createdDate.between(condition.getStartDate(), condition.getEndDate()));
        }

        return queryFactory
                .select(new QDiningReviewConditionSearchForSummaryResponse(
                        diningReview.tasteRating,
                        diningReview.kindnessRating,
                        diningReview.cleanlinessRating,
                        diningReview.atmosphereRating,
                        diningReview.review,
                        diningReview.createdDate,
                        dishReservation.restaurant
                        ))
                .from(diningReview)
                .join(diningReview.dishReservation, dishReservation)
                .where(builder)
                .fetch();
    }

    @Override
    public Long countByExpression(DiningReviewConditionSearchRequest condition) {
        QDiningReview diningReview = QDiningReview.diningReview;
        QDishReservation dishReservation = QDishReservation.dishReservation;
        BooleanBuilder builder = new BooleanBuilder();

        if(condition.getRestaurant() != null) {
            builder.and(dishReservation.restaurant.eq(condition.getRestaurant()));
        }

        if (condition.getStartDate() != null && condition.getEndDate() != null) {
            builder.and(diningReview.createdDate.between(condition.getStartDate(), condition.getEndDate()));
        }

        return queryFactory
                .select(diningReview.count().longValue())
                .from(diningReview)
                .join(diningReview.dishReservation, dishReservation)
                .where(builder)
                .fetchOne();

    }
}
