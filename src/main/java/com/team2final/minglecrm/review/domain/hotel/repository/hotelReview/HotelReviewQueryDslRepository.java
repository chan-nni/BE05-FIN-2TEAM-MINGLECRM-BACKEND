package com.team2final.minglecrm.review.domain.hotel.repository.hotelReview;

import com.team2final.minglecrm.ai.dto.vo.HotelReviewForSummary;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewForSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface HotelReviewQueryDslRepository {
    Page<HotelReviewConditionSearchResponse> searchByExpression(HotelReviewConditionSearchRequest condition, Pageable pageable);
    List<HotelReviewForSummary> findAllByStartDateCondition(LocalDateTime startDate);
    List<HotelReviewForSummaryResponse> findHotelReviewsByCondition(HotelReviewConditionSearchRequest condition);
}
