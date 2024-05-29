package com.team2final.minglecrm.controller.review;

import com.team2final.minglecrm.persistence.repository.hotel.HotelReviewRepository;
import com.team2final.minglecrm.service.review.HotelReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelReviewController {

    private final HotelReviewService hotelReviewService;

    @GetMapping("/api/hotel/reviews/{id}")
    public ResponseEntity<Void> getHotelReviewsWithPageNum(@PathVariable("id") int id) {
        hotelReviewService.findAllHotelReivewsWithPaging(id);
        return null;
    }
}
