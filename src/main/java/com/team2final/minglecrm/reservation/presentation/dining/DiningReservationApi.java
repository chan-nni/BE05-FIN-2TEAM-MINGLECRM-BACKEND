package com.team2final.minglecrm.reservation.presentation.dining;

import com.team2final.minglecrm.reservation.dto.dining.request.UpdateDiningReservationRequest;
import com.team2final.minglecrm.reservation.dto.dining.response.DiningReservationResponse;
import com.team2final.minglecrm.reservation.service.dining.DishReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}/dish/reservations")
public class DiningReservationApi {
    private final DishReservationService dishReservationService;

    // 다이닝(디시) 전체 예약 내역
    @GetMapping()
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<DiningReservationResponse>> getReservationsList(
            @PathVariable Long customerId) {
        List<DiningReservationResponse> responses = dishReservationService.findById(customerId);
        return ResponseEntity.ok(responses);
    }

    // 다이닝(디시) 상세 예약 내역
    @GetMapping("/{reservationId}")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<DiningReservationResponse> getRoomReservation(
            @PathVariable Long customerId, @PathVariable Long reservationId) {
        DiningReservationResponse diningReservationResponse = dishReservationService.findReservationById(
                customerId, reservationId);

        return ResponseEntity.ok(diningReservationResponse);
    }

    // 다이닝(디시) 예약 수정
    @PatchMapping("/{reservationId}")
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public void updateRoomReservation(
            @PathVariable Long customerId,
            @PathVariable Long reservationId,
            @RequestBody UpdateDiningReservationRequest updateDiningReservationRequest) {

        dishReservationService.updateDiningReservation(customerId, reservationId,
                updateDiningReservationRequest);
    }

    // 다이닝(디시) 상세 예약 삭제
    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void cancelRoomReservation(
            @PathVariable Long customerId,
            @PathVariable Long reservationId) {
        dishReservationService.cancelDiningReservation(customerId, reservationId);
    }


}
