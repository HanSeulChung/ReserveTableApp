package com.chs.reservation.service;


import com.chs.exception.Impl.NoApprovalReservationUserException;
import com.chs.exception.Impl.NoReservationFromUserIdException;
import com.chs.exception.Impl.NotBefore10minuteException;
import com.chs.exception.Impl.NotTodayReservationException;
import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.entity.Reservation;
import com.chs.reservation.repository.ReservationRepository;
import com.chs.type.ArriveCode;
import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;

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
    public List<ReservationDto> getReservationByStoreId(Long storeId) {
        var result = reservationRepository.findAllByStoreId(storeId);
        return ReservationDto.of(result);
    }

    @Override
    public List<ReservationDto> getReservationsByUserId(String userId) {
        var result = reservationRepository.findAllByUserId(userId);
        return ReservationDto.of(result);
    }

    @Override
    public ReservationDto getReservationByUserIdOnKiosk(String userId, Long storeId) {
        var result = reservationRepository.findByStoreIdAndUserId(storeId, userId)
                .orElseThrow(() -> new NoReservationFromUserIdException());
        if(!result.getStatus().equals(ReservationCode.APPROVE)) {
            throw new NoApprovalReservationUserException();
        }

        return ReservationDto.of(result);
    }


    @Override
    public ReservationDto arriveOnStore(String userId, Long storeId) {
        var result = reservationRepository.findByStoreIdAndUserId(storeId, userId)
                .orElseThrow(() -> new NoReservationFromUserIdException());

        LocalDateTime nowTime = LocalDateTime.now();

        if (!result.getResDt().toLocalDate().isEqual(nowTime.toLocalDate())) {
            throw new NotTodayReservationException();
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
                .storeId(result.getStoreId())
                .userId(result.getUserId())
                .userPhone(result.getUserPhone())
                .build();
        reservationRepository.save(reservation);

        return ReservationDto.of(reservation);
    }
}
