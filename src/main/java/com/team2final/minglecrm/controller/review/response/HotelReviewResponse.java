package com.team2final.minglecrm.controller.review.response;

import com.team2final.minglecrm.entity.hotel.HotelReview;
import com.team2final.minglecrm.entity.hotel.type.RoomType;
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
