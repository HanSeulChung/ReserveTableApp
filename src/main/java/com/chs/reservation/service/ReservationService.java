package com.chs.reservation.service;

import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

    /**
     * 예약 등록
     */
    ReservationDto reserve(ReservationInput parameter);


    /**
     * 전체 예약 조회
     */
    Page<Reservation> getAllReservations(Pageable pageable);

    /**
     * 승인 예약만 조회
     */

    /**
     * 거부 예약만 조회
     */


    /**
     * 도착 후 키오스크 조작
     */

}
