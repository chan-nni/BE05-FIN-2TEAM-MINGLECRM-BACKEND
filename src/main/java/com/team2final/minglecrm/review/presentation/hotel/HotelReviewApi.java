package com.team2final.minglecrm.review.presentation.hotel;

import com.team2final.minglecrm.common.exception.ResultResponse;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewMetaDataResponse;
import com.team2final.minglecrm.review.service.hotel.HotelReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelReviewApi {

    private final HotelReviewService hotelReviewService;

//    @GetMapping("/api/hotel/reviews/{pageno}")
//    public ResponseEntity<ResultResponse<List<HotelReviewResponse>>> getHotelReviewsWithPageNum(@PathVariable("pageno") int pageNo) {
//        List<HotelReviewResponse> response = hotelReviewService.findAllHotelReviewsWithPaging(pageNo);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse<>(HttpStatusCode.valueOf(HttpStatus.OK.value()).value(), "전체 리뷰 목록 반환 성공", response));
//    }

    @PostMapping("/api/hotel/reviews/{pageno}")
    public ResultResponse<List<HotelReviewConditionSearchResponse>> searchReviews(@PathVariable("pageno") int pageNo, @RequestBody HotelReviewConditionSearchRequest request) {
        return new ResultResponse<>(HttpStatus.OK.value(), "success" ,hotelReviewService.searchReviews(request, pageNo));
    }

    @GetMapping("/api/hotel/review/meta")
    public ResultResponse<HotelReviewMetaDataResponse> getHotelReviewMetaData() {
        HotelReviewMetaDataResponse response = hotelReviewService.getHotelReviewMetaData();
        return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
    }

    @GetMapping("/api/hotel/rating/average")
    public ResultResponse<Double> getHotelAverageRatingByPeriod(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                @RequestParam(name = "hotel") String hotel) {
        Double response = hotelReviewService.getHotelReviewAverageRatingByPeriod(startDate, endDate, hotel);

        return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
    }

    @GetMapping("/api/hotel/review/count")
    public ResultResponse<Long> getHotelReviewsNumberByPeriod(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                     @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                     @RequestParam(name = "hotel") String hotel) {
        Long response = hotelReviewService.getHotelReviewsNumberByPeriod(startDate, endDate, hotel);
        return new ResultResponse<>(HttpStatus.OK.value(), "success", response);
    }
}
