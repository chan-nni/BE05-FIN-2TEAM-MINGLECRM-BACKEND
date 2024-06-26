package com.team2final.minglecrm.ai.presentation;

import com.team2final.minglecrm.ai.dto.request.HotelReviewSummaryRequest;
import com.team2final.minglecrm.ai.dto.response.DiningReviewSummaryResponse;
import com.team2final.minglecrm.ai.dto.response.HotelReviewSummaryResponse;
import com.team2final.minglecrm.ai.dto.vo.JoinedReviews;
import com.team2final.minglecrm.ai.service.AiService;
import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.review.domain.dining.DiningReviewSummary;
import com.team2final.minglecrm.review.domain.hotel.SummaryType;
import com.team2final.minglecrm.review.service.dining.DiningReviewService;
import com.team2final.minglecrm.review.service.hotel.HotelReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AiApi {

    private final AiService aiService;
    private final HotelReviewService hotelReviewService;
    private final DiningReviewService diningReviewService;

    @PostMapping("/api/hotel/review/summary")
    ResultResponse<String> createHotelReviewSummary(@RequestBody HotelReviewSummaryRequest request) {
        String hotel = request.getHotel();
        if (request.getHotel().equals("All")) {
            hotel = null;
        }
        JoinedReviews joinedHotelReviews = hotelReviewService.getJoinedHotelReviews(request.getStartDate(), request.getEndDate(), hotel);
        if (joinedHotelReviews == null) {
            return new ResultResponse<>(HttpStatus.BAD_REQUEST.value(), "리뷰 데이터 없음", null);
        }

        String answer = aiService.createHotelReviewSummary(joinedHotelReviews, request.getSummaryType(), request.getHotel());
        return new ResultResponse<>(HttpStatus.CREATED.value(), "success", answer);
    }

    @PostMapping("/api/dining/review/summary")
    ResultResponse<String> createDiningReviewSummary(@RequestBody DiningReviewSummary request) {
        String restaurant = request.getRestaurant();
        if (request.getRestaurant().equals("All")){
            restaurant = null;
        }

        JoinedReviews joinedDiningReviews = diningReviewService.getJoinedDiningReviews(request.getStartDate(), request.getEndDate(), restaurant);

        if (joinedDiningReviews == null) {
            return new ResultResponse<>(HttpStatus.BAD_REQUEST.value(), "리뷰 데이터 없음", null);
        }

        String answer = aiService.createDiningReviewSummary(joinedDiningReviews, request.getSummaryType(), request.getRestaurant());
        return new ResultResponse<>(HttpStatus.CREATED.value(), "success", answer);
    }

    @GetMapping("/api/hotel/review/summary")
    ResultResponse<HotelReviewSummaryResponse> getHotelReviewSummary(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            @RequestParam SummaryType summaryType,
            @RequestParam String hotel) {
        HotelReviewSummaryResponse summary = aiService.getHotelReviewSummaryByPeriod(startDate, endDate, summaryType, hotel);

        if (summary == null) {
            return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
        }

        return new ResultResponse<>(HttpStatus.OK.value(), "success", summary);
    }

    @GetMapping("/api/dining/review/summary")
    ResultResponse<DiningReviewSummaryResponse> getDiningReviewSummary(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            @RequestParam SummaryType summaryType,
            @RequestParam String restaurant
    ) {
        DiningReviewSummaryResponse summary = aiService.getDiningReviewSummaryByPeriod(startDate, endDate, summaryType, restaurant);

        if (summary == null) {
            return new ResultResponse<>(HttpStatus.OK.value(), "success", null);
        }

        return new ResultResponse<>(HttpStatus.OK.value(), "success", summary);
    }

}
