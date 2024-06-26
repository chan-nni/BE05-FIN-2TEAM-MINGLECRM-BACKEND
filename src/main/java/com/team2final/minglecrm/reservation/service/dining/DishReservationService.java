package com.team2final.minglecrm.reservation.service.dining;

import com.team2final.minglecrm.reservation.domain.dining.Dish;
import com.team2final.minglecrm.reservation.domain.dining.DishReservation;
import com.team2final.minglecrm.reservation.domain.dining.repository.DishRepository;
import com.team2final.minglecrm.reservation.domain.dining.repository.DishReservationRepository;
import com.team2final.minglecrm.reservation.dto.dining.request.UpdateDiningReservationRequest;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishReservationService {

    private final DishReservationRepository dishReservationRepository;
    private final DishRepository dishRepository;

    public List<DiningReservationResponse> findById(Long customerId) {
        List<DishReservation> dishReservations = dishReservationRepository.findByCustomerId(
                customerId);

        return dishReservations.stream()
                .map(dishReservation -> {
                    List<Dish> dishes = dishRepository.findByDishReservationsId(dishReservation.getId());
                    return DiningReservationResponse.of(dishReservation, dishes);
                })
                .collect(Collectors.toList());
    }

    public DiningReservationResponse findReservationById(Long customerId, Long reservationId) {
        DishReservation dishReservation = getDishReservation(reservationId);
        vaildCheck(customerId, dishReservation);
        List<Dish> dishes = dishRepository.findByDishReservationsId(reservationId);

        return DiningReservationResponse.of(dishReservation, dishes);
    }

    public void updateDiningReservation(Long customerId, Long reservationId,
            UpdateDiningReservationRequest updateDiningReservationRequest) {

        DishReservation dishReservation = getDishReservation(reservationId);
        vaildCheck(customerId, dishReservation);

        dishReservation.updateDishReservation(updateDiningReservationRequest);
    }

    public void cancelDiningReservation(Long customerId, Long reservationId) {
        DishReservation dishReservation = getDishReservation(reservationId);
        vaildCheck(customerId, dishReservation);
        dishReservation.cancelDishReservation();
    }

    private DishReservation getDishReservation(Long reservationId) {
        DishReservation dishReservation = dishReservationRepository.findById(reservationId)
                .orElseThrow();

        return dishReservation;
    }

    private static void vaildCheck(Long customerId, DishReservation dishReservation) {
        if (!Objects.equals(dishReservation.getCustomer().getId(), customerId)) {
            throw new RuntimeException("정보가 맞지 않습니다.");
        }
    }

}
