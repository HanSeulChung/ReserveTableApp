package com.chs.reservation.service;


import com.chs.exception.Impl.*;
import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.service.StoreService;
import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.entity.Reservation;
import com.chs.reservation.repository.ReservationRepository;
import com.chs.type.ArriveCode;
import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final StoreService storeService;

    @Override
    public ReservationDto reserve(ReservationInput parameter) {
        ReservationDto reservationDto = ReservationDto.fromInput(parameter);
        reservationRepository.save(Reservation.toEntity(reservationDto));
        return reservationDto;
    }

//    @Override
//    public Page<Reservation> getAllReservations(Pageable pageable) {
//
//        return this.reservationRepository.findAll(pageable);
//    }


    @Override
    public Map<Long, List<ReservationDto>> getReservationByOwnerId(String ownerId) {
        List<StoreDto> storeByOwnerId = this.storeService.getStoreByOwnerId(ownerId);
        Map<Long, List<ReservationDto>> reservationMapByOwnerId = new HashMap<>();

        for (int i = 0; i < storeByOwnerId.size(); i++) {
            reservationMapByOwnerId.put(storeByOwnerId.get(i).getStoreId() ,ReservationDto.of(reservationRepository.findAllByStore_Id(storeByOwnerId.get(i).getStoreId())));
        }

        return reservationMapByOwnerId;
    }

    @Override
    public List<ReservationDto> getReservationByOwnerIdAndStoreId(String ownerId, Long storeId) {
        storeService.getStoreByOwnerId(ownerId);
        var result = reservationRepository.findAllByStore_Id(storeId);
        return ReservationDto.of(result);
    }

    @Override
    public List<ReservationDto> getReservationByStoreId(Long storeId) {
        var result = reservationRepository.findAllByStore_Id(storeId);
        return ReservationDto.of(result);
    }

    @Override
    public ReservationDto approvalReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationException());

        Reservation saveReservation = reservationRepository.save(
                Reservation.builder()
                        .id(reservation.getId())
                        .regDt(reservation.getRegDt())
                        .resDt(reservation.getResDt())
                        .status(ReservationCode.APPROVE)
                        .usingCode(reservation.getUsingCode())
                        .arriveCode(reservation.getArriveCode())
                        .arrDt(reservation.getArrDt())
                        .build()
        );
        return ReservationDto.of(saveReservation);
    }

    @Override
    public ReservationDto refuseReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationException());

        Reservation saveReservation = reservationRepository.save(
                Reservation.builder()
                        .id(reservation.getId())
                        .regDt(reservation.getRegDt())
                        .resDt(reservation.getResDt())
                        .status(ReservationCode.REFUSE)
                        .usingCode(reservation.getUsingCode())
                        .arriveCode(reservation.getArriveCode())
                        .arrDt(reservation.getArrDt())
                        .build()
        );
        return ReservationDto.of(saveReservation);
    }

    @Override
    public List<ReservationDto> getReservationsByUserId(String userId) {
        var result = reservationRepository.findAllByUser_UserId(userId);
        return ReservationDto.of(result);
    }

    @Override
    public List<ReservationDto> getReservationByUserIdOnKiosk(String userId, Long storeId) {
        var result = reservationRepository.findAllByStore_IdAndUser_UserId(storeId, userId);

        if (result == null) {
            throw new NoReservationFromUserIdException();
        }

        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation x : result) {
            if (x.getStatus().equals(ReservationCode.APPROVE)) {
                reservationDtoList.add(ReservationDto.of(x));
            }
        }

        if (reservationDtoList.size() == 0) {
            throw new NoApprovalReservationUserException();
        }

        return reservationDtoList;
    }




    @Override
    public ReservationDto arriveOnStore(Long reservationId) {
        var result = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationFromUserIdException());

        LocalDateTime nowTime = LocalDateTime.now();

        if (result.getResDt().toLocalDate().isAfter(nowTime.toLocalDate())) {
            throw new EarlydayThanReservationException();
        }

        if (result.getResDt().toLocalDate().isBefore(nowTime.toLocalDate())) {
            throw new AfterdayThanReservationException();
        }

        LocalTime arrivalTime = nowTime.toLocalTime();
        LocalTime reservationTime = result.getResDt().toLocalTime();

        if (Duration.between(arrivalTime, reservationTime).toMinutes() < 10) {
            throw new NotBefore10minuteException();
        }

        Reservation reservation = result.builder()
                .id(result.getId())
                .arrDt(nowTime)
                .usingCode(UsingCode.COMPLETE)
                .regDt(result.getRegDt())
                .resDt(result.getResDt())
                .arriveCode(ArriveCode.CORRECT_ARRIVAL)
                .status(result.getStatus())
                .build();
        reservationRepository.save(reservation);

        return ReservationDto.of(reservation);
    }
}
