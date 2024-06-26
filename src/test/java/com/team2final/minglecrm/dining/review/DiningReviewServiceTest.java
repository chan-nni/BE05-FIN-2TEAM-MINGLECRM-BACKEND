//package com.team2final.minglecrm.dining.review;
//
//
//import com.team2final.minglecrm.reservation.dto.dining.response.DiningReviewResponse;
//import com.team2final.minglecrm.customer.domain.Customer;
//import com.team2final.minglecrm.review.domain.dining.DiningReview;
//import com.team2final.minglecrm.reservation.domain.dining.DishReservation;
//import com.team2final.minglecrm.customer.domain.repository.CustomerRepository;
//import com.team2final.minglecrm.review.domain.dining.repository.DiningReviewRepository;
//import com.team2final.minglecrm.reservation.domain.dining.repository.DishReservationRepository;
//import com.team2final.minglecrm.review.service.dining.DiningReviewService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@SpringBootTest
//class DiningReviewServiceTest {
//
//    @Autowired
//    private DiningReviewService diningReviewService;
//
//    @Autowired
//    private DiningReviewRepository diningReviewRepository;
//
//    @Autowired
//    private DishReservationRepository dishReservationRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//
//   @BeforeEach
//   void setUp() {
//       Customer customer = Customer.builder()
//               .name("customer1")
//               .grade("SILVER")
//               .phone("01012341234")
//               .address("서울시")
//               .birth(LocalDate.of(1998,9,18))
//               .build();
//
//       customerRepository.save(customer);
//
//       DishReservation dishReservation1 = DishReservation.builder()
//               .totalPrice(10000L)
//               .reservationDate(LocalDateTime.now())
//               .customer(customer)
//               .build();
//
//       DishReservation dishReservation2 = DishReservation.builder()
//               .totalPrice(10000L)
//               .reservationDate(LocalDateTime.now())
//               .customer(customer)
//               .build();
//
//       dishReservationRepository.save(dishReservation1);
//       dishReservationRepository.save(dishReservation2);
//
//       DiningReview diningReview1 = DiningReview.builder()
//               .atmosphereRating(3.5)
//               .kindnessRating(2.5)
//               .cleanlinessRating(1.5)
//               .tasteRating(5.0)
//               .review("정말 맛있엇습니다")
//               .customer(customer)
//               .dishReservation(dishReservation1)
//               .build();
//
//       DiningReview diningReview2 = DiningReview.builder()
//               .atmosphereRating(3.5)
//               .kindnessRating(2.5)
//               .cleanlinessRating(1.5)
//               .tasteRating(5.0)
//               .review("정말 맛있엇습니다")
//               .customer(customer)
//               .dishReservation(dishReservation2)
//               .build();
//
//       diningReviewRepository.save(diningReview1);
//       diningReviewRepository.save(diningReview2);
//   }
//
////   @Test
////   void findAllDiningReviewsWithPaging() {
////
////       // Given
////
////
////       // When
////       List<DiningReviewResponse> reviews = diningReviewService.findAllDiningReviewsWithPaging(0);
////
////       // Then
////       Assertions.assertEquals(2, reviews.size());
////
////   }
//
//}
