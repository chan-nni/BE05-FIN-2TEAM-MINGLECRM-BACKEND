package com.team2final.minglecrm.reservation.presentation.hotel;

import com.team2final.minglecrm.reservation.dto.hotel.request.UpdateRoomReservationRequest;
import com.team2final.minglecrm.reservation.dto.hotel.response.RoomReservationResponse;
import com.team2final.minglecrm.reservation.service.hotel.HotelReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}/hotel/reservations")
public class HotelReservationDetailApi {

    private final HotelReservationService hotelReservationService;

    // 호텔 전체 예약 내역
    @GetMapping()
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<List<RoomReservationResponse>> getReservationsList(
            @PathVariable Long customerId) {
        List<RoomReservationResponse> responses = hotelReservationService.findById(customerId);
        return ResponseEntity.ok(responses);
    }

    // 호텔 내역 상세보기
    @GetMapping("/{reservationId}")
//    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public ResponseEntity<RoomReservationResponse> getRoomReservation(
            @PathVariable Long customerId, @PathVariable Long reservationId) {
        RoomReservationResponse roomReservationResponse = hotelReservationService.findReservationById(
                customerId, reservationId);

        return ResponseEntity.ok(roomReservationResponse);
    }


    // 호텔 내역 수정
    @PatchMapping("/{reservationId}")
    @PreAuthorize("hasAnyRole('STAFF', 'MANAGER')")
    public void updateRoomReservation(
            @PathVariable Long customerId,
            @PathVariable Long reservationId,
            @RequestBody UpdateRoomReservationRequest updateRoomReservationRequest) {

        hotelReservationService.updateHotelReservation(customerId, reservationId,
                updateRoomReservationRequest);
    }

    // 호텔 내역 삭제
    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasRole('MANAGER')")
    public void cancelRoomReservation(
            @PathVariable Long customerId,
            @PathVariable Long reservationId) {
        hotelReservationService.cancelHotelReservation(customerId, reservationId);
    }



}
