package com.team2final.minglecrm.review.dto.hotel.response;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class HotelReviewForSummaryResponse {

    private Double kindnessRating;
    private Double cleanlinessRating;
    private Double convenienceRating;
    private Double locationRating;
    private String comment;
    private LocalDateTime createdTime;

    @QueryProjection
    public HotelReviewForSummaryResponse(
            Double kindnessRating,
            Double cleanlinessRating,
            Double convenienceRating,
            Double locationRating,
            String comment,
            LocalDateTime createdTime
    ) {
        this.kindnessRating = kindnessRating;
        this.cleanlinessRating = cleanlinessRating;
        this.convenienceRating = convenienceRating;
        this.locationRating = locationRating;
        this.comment = comment;
        this.createdTime = createdTime;
    }

}
