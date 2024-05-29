package com.team2final.minglecrm.service.review;

import com.team2final.minglecrm.controller.review.response.HotelReviewResponse;
import com.team2final.minglecrm.entity.hotel.HotelReview;
import com.team2final.minglecrm.persistence.repository.hotel.HotelReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelReviewService {

    private final HotelReviewRepository hotelReviewRepository;

    public List<HotelReviewResponse> findAllHotelReivewsWithPaging(int pageNo) {
        Page<HotelReview> hotelReviewPage = hotelReviewRepository.findAll(PageRequest.of(pageNo, 9));
        System.out.println(hotelReviewPage.getContent());
        return null;
    }
}
