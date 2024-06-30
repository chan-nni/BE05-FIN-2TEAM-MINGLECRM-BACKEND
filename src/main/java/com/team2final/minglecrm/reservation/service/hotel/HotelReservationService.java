package com.team2final.minglecrm.reservation.service.hotel;

import com.team2final.minglecrm.reservation.domain.hotel.repository.RoomReservationQueryDslRepository;
import com.team2final.minglecrm.reservation.domain.hotel.repository.RoomReservationQueryDslRepositoryImpl;
import com.team2final.minglecrm.reservation.dto.hotel.request.UpdateRoomReservationRequest;
import com.team2final.minglecrm.reservation.dto.hotel.response.RoomReservationResponse;
import com.team2final.minglecrm.reservation.domain.hotel.RoomReservation;
import com.team2final.minglecrm.reservation.domain.hotel.repository.RoomReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.team2final.minglecrm.reservation.dto.hotel.response.RoomReservationStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelReservationService {

    private final RoomReservationRepository roomReservationRepository;
    private final RoomReservationQueryDslRepository roomReservationQueryDslRepository;

    @Transactional
    public List<RoomReservationResponse> findById(Long customerId) {
//        List<RoomReservationResponse> roomReservationResponses = new ArrayList<>();
        List<RoomReservation> roomReservations = roomReservationRepository.findByCustomerId(
                customerId);
//
//        for (RoomReservation roomReservation : roomReservations) {
//            RoomReservationResponse roomReservationResponse = RoomReservationResponse.builder()
//                    .name(roomReservation.getCustomer().getName())
//                    .reservationDate(roomReservation.getReservationDate())
//                    .memo(roomReservation.getCustomer().getMemo())
//                    .build();
//            roomReservationResponses.add(roomReservationResponse);
//        }

        return roomReservations
                .stream()
                .map(RoomReservationResponse::of)
                .collect(Collectors.toList());

//        return roomReservationResponses;
    }

    @Transactional
    public RoomReservationResponse findReservationById(Long customerId, Long reservationId) {

        RoomReservation roomReservation = roomReservationRepository.findById(reservationId)
                .orElseThrow();

        if (!Objects.equals(roomReservation.getCustomer().getId(), customerId)) {
            throw new RuntimeException("알맞은 정보가 없습니다.");
        }

        return RoomReservationResponse.of(roomReservation);

//        return RoomReservationResponse.builder()
//                .memo(roomReservation.getCustomer().getMemo())
//                .reservationDate(roomReservation.getReservationDate())
//                .name(roomReservation.getCustomer().getName())
//                .build();
    }

    @Transactional
    public void updateHotelReservation(Long customerId, Long reservationId,
            UpdateRoomReservationRequest updateRoomReservationRequest) {

        RoomReservation roomReservation = roomReservationRepository.findById(reservationId)
                .orElseThrow();

        if (!Objects.equals(roomReservation.getCustomer().getId(), customerId)) {
            throw new RuntimeException("알맞은 정보가 없습니다.");
        }

        roomReservation.updateHotelReservationInfo(updateRoomReservationRequest);
    }

    @Transactional
    public void cancelHotelReservation(Long customerId, Long reservationId) {
        RoomReservation roomReservation = roomReservationRepository.findById(reservationId)
                .orElseThrow();

        if (!Objects.equals(roomReservation.getCustomer().getId(), customerId)) {
            throw new RuntimeException("알맞은 정보가 없습니다.");
        }
        roomReservation.deleteHotelReservation();
    }

    @Transactional
    public RoomReservationStatisticsResponse getRoomReservationStatistics(LocalDate startDate, LocalDate endDate) {
        return roomReservationQueryDslRepository.findRoomReservationStatistics(startDate, endDate);
    }

}
