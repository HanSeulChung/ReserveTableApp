package com.chs.reservation.service;

import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {

    /**
     * 예약 등록
     */
    ReservationDto reserve(ReservationInput parameter);


    /**
     * 가게 별로 예약 조회 By 점주 마이페이지
     */
    List<ReservationDto> getReservationByStoreId(Long storeId);

    /**
     * 전체 예약 조회 By 사용자 마이페이지
     */
    List<ReservationDto> getReservationsByUserId(String userId);

    /**
     * 가게 앞 키오스크
     */
    ReservationDto getReservationByUserIdOnKiosk(String userId, Long storeId);

    /**
     * 승인 예약만 조회
     */

    /**
     * 거부 예약만 조회
     */


    /**
     * 도착 후 키오스크 조작
     */
    ReservationDto arriveOnStore(String userId, Long storeId);
}
