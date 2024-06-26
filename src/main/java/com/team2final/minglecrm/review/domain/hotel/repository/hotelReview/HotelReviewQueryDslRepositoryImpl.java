package com.team2final.minglecrm.review.domain.hotel.repository.hotelReview;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team2final.minglecrm.ai.dto.vo.HotelReviewForSummary;
import com.team2final.minglecrm.ai.dto.vo.QHotelReviewForSummary;
import com.team2final.minglecrm.customer.domain.QCustomer;
import com.team2final.minglecrm.reservation.domain.hotel.QHotelRoom;
import com.team2final.minglecrm.reservation.domain.hotel.QRoomReservation;
import com.team2final.minglecrm.review.domain.hotel.QHotelReview;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewForSummaryResponse;
import com.team2final.minglecrm.review.dto.hotel.response.QHotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.QHotelReviewForSummaryResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class HotelReviewQueryDslRepositoryImpl implements HotelReviewQueryDslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public HotelReviewQueryDslRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<HotelReviewConditionSearchResponse> searchByExpression(HotelReviewConditionSearchRequest condition, Pageable pageable) {

        QHotelReview hotelReview = QHotelReview.hotelReview;
        QCustomer customer = QCustomer.customer;
        QRoomReservation roomReservation = QRoomReservation.roomReservation;
        QHotelRoom hotelRoom = QHotelRoom.hotelRoom;

        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getCustomerName() != null) {
            builder.and(customer.name.eq(condition.getCustomerName()));
        }
        if (condition.getHotel() != null) {
            builder.and(hotelRoom.hotel.eq(condition.getHotel())); // Hotel 정보 검색
        }
        if (condition.getRoomType() != null) {
            builder.and(hotelRoom.roomType.eq(condition.getRoomType()));
        }
        if (condition.getStartDate() != null && condition.getEndDate() != null) {
            builder.and(hotelReview.createdTime.between(condition.getStartDate(), condition.getEndDate()));
        }

        List<HotelReviewConditionSearchResponse> response =  queryFactory
                .select(new QHotelReviewConditionSearchResponse(
                        customer.name,
                        hotelReview.kindnessRating,
                        hotelReview.cleanlinessRating,
                        hotelReview.convenienceRating,
                        hotelReview.locationRating,
                        hotelReview.comment,
                        hotelReview.createdTime,
                        roomReservation.hotelRoom.roomType,
                        hotelRoom.hotel // Hotel 정보 추가
                ))
                .from(hotelReview)
                .join(hotelReview.customer, customer)
                .join(hotelReview.roomReservation, roomReservation)
                .join(roomReservation.hotelRoom, hotelRoom) // HotelRoom과 조인
                .where(builder)
                .offset(pageable.getOffset()) // offset
                .limit(pageable.getPageSize()) // limit
                .fetch();


        return new PageImpl<>(response, pageable, response.size());
    }

    public List<HotelReviewForSummary> findAllByStartDateCondition(LocalDateTime startDate) {

        QHotelReview hotelReview = QHotelReview.hotelReview;


        return queryFactory
                .select(new QHotelReviewForSummary(
                        hotelReview.comment
                ))
                .from(hotelReview)
                .where(hotelReview.createdTime.after(startDate))
                .fetch();
    }

    public List<HotelReviewForSummaryResponse> findHotelReviewsByCondition(HotelReviewConditionSearchRequest condition) {
        QHotelReview hotelReview = QHotelReview.hotelReview;
        QHotelRoom hotelRoom = QHotelRoom.hotelRoom;
        QRoomReservation roomReservation = QRoomReservation.roomReservation;

        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getHotel() != null) {
            builder.and(hotelRoom.hotel.eq(condition.getHotel())); // Hotel 정보 검색
        }

        if (condition.getStartDate() != null && condition.getEndDate() != null) {
            builder.and(hotelReview.createdTime.between(condition.getStartDate(), condition.getEndDate()));
        }

        return queryFactory
                .select(new QHotelReviewForSummaryResponse(
                        hotelReview.kindnessRating,
                        hotelReview.cleanlinessRating,
                        hotelReview.convenienceRating,
                        hotelReview.locationRating,
                        hotelReview.comment,
                        hotelReview.createdTime
                ))
                .from(hotelReview)
                .join(hotelReview.roomReservation, roomReservation)
                .join(roomReservation.hotelRoom, hotelRoom) // HotelRoom과 조인
                .where(builder)
                .fetch();
    }

}
