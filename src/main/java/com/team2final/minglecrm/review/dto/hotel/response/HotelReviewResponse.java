package com.team2final.minglecrm.review.dto.hotel.response;

import com.team2final.minglecrm.reservation.domain.hotel.RoomType;
import com.team2final.minglecrm.review.domain.hotel.HotelReview;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HotelReviewResponse {

    private String customerName;
    private Double kindnessRating;
    private Double cleanlinessRating;
    private Double convenienceRating;
    private Double locationRating;
    private String comment;
    private LocalDateTime createdTime;
    private RoomType roomType;

    @Builder
    public HotelReviewResponse(String customerName, Double kindnessRating, Double cleanlinessRating, Double convenienceRating, Double locationRating,
                               String comment, LocalDateTime createdTime, RoomType roomType) {

        this.customerName = customerName;
        this.kindnessRating = kindnessRating;
        this.cleanlinessRating = cleanlinessRating;
        this.convenienceRating = convenienceRating;
        this.locationRating = locationRating;
        this.comment = comment;
        this.createdTime = createdTime;
        this.roomType = roomType;
    }

    public static HotelReviewResponse of(HotelReview hotelReview) {

        return HotelReviewResponse.builder()
                .customerName(hotelReview.getCustomer().getName())
                .kindnessRating(hotelReview.getKindnessRating())
                .cleanlinessRating(hotelReview.getCleanlinessRating())
                .convenienceRating(hotelReview.getConvenienceRating())
                .locationRating(hotelReview.getLocationRating())
                .comment(hotelReview.getComment())
                .createdTime(hotelReview.getCreatedTime())
                .roomType(hotelReview.getRoomReservation().getHotelRoom().getRoomType())
                .build();
    }

}
