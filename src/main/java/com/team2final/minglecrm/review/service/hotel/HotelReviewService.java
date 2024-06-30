package com.team2final.minglecrm.review.service.hotel;

import com.team2final.minglecrm.ai.dto.vo.JoinedReviews;
import com.team2final.minglecrm.review.domain.hotel.HotelReview;
import com.team2final.minglecrm.review.domain.hotel.repository.hotelReview.HotelReviewRepository;
import com.team2final.minglecrm.review.dto.hotel.request.HotelReviewConditionSearchRequest;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewConditionSearchResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewForSummaryResponse;
import com.team2final.minglecrm.review.dto.hotel.response.HotelReviewMetaDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelReviewService {

    private final HotelReviewRepository hotelReviewRepository;
    private final int ROWS_PER_PAGE = 9;

    public List<HotelReviewConditionSearchResponse> searchReviews(HotelReviewConditionSearchRequest condition, int pageNo) {

        Page<HotelReviewConditionSearchResponse> page =  hotelReviewRepository.searchByExpression(condition, PageRequest.of(pageNo, ROWS_PER_PAGE));
        List<HotelReviewConditionSearchResponse> response = new ArrayList<>();

        for(HotelReviewConditionSearchResponse hotelReview : page.getContent() ) {
            response.add(hotelReview);
        }

        return response;
    }

    public JoinedReviews getJoinedHotelReviews (LocalDateTime startDate, LocalDateTime endDate, String hotel) {
        HotelReviewConditionSearchRequest request = new HotelReviewConditionSearchRequest(null, hotel, null, startDate, endDate);
        List<HotelReviewForSummaryResponse> hotelReviewList = hotelReviewRepository.findHotelReviewsByCondition(request);
        StringBuilder response = new StringBuilder();

        if (hotelReviewList.isEmpty()) {
            System.out.println("리뷰 데이터가 없습니다.");
            return null;
        }

        for (int i=0; i < hotelReviewList.size() ; i++) {
            response.append(i)
                    .append(" 번째 리뷰 : ")
                    .append(hotelReviewList.get(i).getComment())
                    .append("\n");
        }
        return JoinedReviews.builder()
                .joinedReviews(response.toString())
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public HotelReviewMetaDataResponse getHotelReviewMetaData() {
        long rowsNumber = hotelReviewRepository.count();
        return HotelReviewMetaDataResponse.builder()
                        .rowsNumber(rowsNumber)
                        .pagesNumber((long) Math.ceil((double) rowsNumber /ROWS_PER_PAGE))
                        .build();

    }

    public Double getHotelReviewAverageRatingByPeriod(LocalDateTime startDate, LocalDateTime endDate, String hotel) {

        if (hotel.equals("All")) {
            hotel = null;
        }

        HotelReviewConditionSearchRequest request = new HotelReviewConditionSearchRequest(null, hotel, null, startDate, endDate);
        List<HotelReviewForSummaryResponse> hotelReviews = hotelReviewRepository.findHotelReviewsByCondition(request);

        double numberOfReviews = (double) hotelReviews.size();
        double averageRating = 0;
        for(HotelReviewForSummaryResponse hotelReview : hotelReviews) {
            averageRating += getAverageRating(hotelReview);
        }
        return averageRating / numberOfReviews;
    }

    public Long getHotelReviewsNumberByPeriod(LocalDateTime startDate, LocalDateTime endDate, String hotel) {

        if (hotel.equals("All")) {
            hotel = null;
        }

        HotelReviewConditionSearchRequest request = new HotelReviewConditionSearchRequest(null, hotel, null, startDate, endDate);
        List<HotelReviewForSummaryResponse> hotelReviews = hotelReviewRepository.findHotelReviewsByCondition(request);


        return (long) hotelReviews.size();
    }

    public Double getAverageRating(HotelReview hotelReview) {
        return (hotelReview.getCleanlinessRating() +
                hotelReview.getConvenienceRating() +
                hotelReview.getKindnessRating() +
                hotelReview.getLocationRating())/4;
    }

    public Double getAverageRating(HotelReviewForSummaryResponse hotelReview) {
        return (hotelReview.getCleanlinessRating() +
                hotelReview.getConvenienceRating() +
                hotelReview.getKindnessRating() +
                hotelReview.getLocationRating())/4;
    }


}
