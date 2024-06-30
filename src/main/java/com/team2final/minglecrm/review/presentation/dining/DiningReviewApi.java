package com.team2final.minglecrm.review.presentation.dining;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.dining.request.DiningReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.dining.response.DiningReviewMetaDataResponse;
import com.team2final.minglecrm.review.service.dining.DiningReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiningReviewApi {

    private final DiningReviewService diningReviewService;

    @PostMapping("/api/dining/reviews/{pageno}")
    public ResultResponse<List<DiningReviewConditionSearchResponse>> searchDiningReviews(@PathVariable("pageno") Integer pageNo, @RequestBody DiningReviewConditionSearchRequest condition){
        System.out.println(condition);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", diningReviewService.searchDiningReviews(pageNo, condition));
    }

    @GetMapping("/api/dining/review/meta")
    public ResultResponse<DiningReviewMetaDataResponse> getDiningReviewMetaData() {
        DiningReviewMetaDataResponse response = diningReviewService.getDiningReviewMetaData();
        return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
    }

    @GetMapping("/api/dining/rating/average")
    public ResultResponse<Double> getDiningAverageRatingByPeriod(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                 @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                 @RequestParam(name = "restaurant") String restaurant) {

        Double response =  diningReviewService.getDiningReviewAverageRatingByPeriodAndRestaurant(startDate, endDate, restaurant);

        return new ResultResponse<>(HttpStatus.OK.value(), "sucess", response);
    }

    @GetMapping("/api/dining/review/count")
    public ResultResponse<Long> getDiningReviewsNumberByPeriod(
                                                @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                @RequestParam(name = "restaurant") String restaurant) {

        Long response = diningReviewService.getDiningReviewsNumberByPeriod(startDate, endDate, restaurant);
        return new ResultResponse<>(HttpStatus.OK.value(), "sucess", response);
    }


}
