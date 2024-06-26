package com.team2final.minglecrm.ai.dto.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JoinedReviews {

    private final String joinedReviews;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    @Builder
    public JoinedReviews(String joinedReviews, LocalDateTime startDate, LocalDateTime endDate) {
        this.joinedReviews = joinedReviews;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
