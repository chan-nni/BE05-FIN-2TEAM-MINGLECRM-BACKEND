package com.team2final.minglecrm.persistence.repository.hotel;


import com.team2final.minglecrm.entity.hotel.HotelReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelReviewRepository extends JpaRepository<HotelReview, Long> {

    Page<HotelReview> findAll(Pageable pageable);

}
