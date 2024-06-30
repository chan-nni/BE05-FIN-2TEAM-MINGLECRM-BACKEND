package com.team2final.minglecrm.review.dto.dining.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DiningReviewConditionSearchForSummaryResponse {

    private Double tasteRating;
    private Double kindnessRating;
    private Double cleanlinessRating;
    private Double atmosphereRating;

    private String review;
    private LocalDateTime createdTime;
    private String restaurant;


    @QueryProjection
    public DiningReviewConditionSearchForSummaryResponse(
            Double tasteRating,
            Double kindnessRating,
            Double cleanlinessRating,
            Double atmosphereRating,
            String review,
            LocalDateTime createdTime,
            String restaurant
    ) {
        this.tasteRating = tasteRating;
        this.kindnessRating = kindnessRating;
        this.cleanlinessRating = cleanlinessRating;
        this.atmosphereRating = atmosphereRating;
        this.review = review;
        this.createdTime = createdTime;
        this.restaurant = restaurant;
    }
}
