package com.chs.reservation.service;

import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ReservationService {

    // thymeleaf
    /**
     * 예약 등록
     */
    ReservationDto reserve(ReservationInput parameter);

    /**
     * 예약 삭제
     */
    boolean delete(String idList);

    /**
     * 예약 수정
     */
    boolean edit(ReservationDto parameter);

    /**
     * 예약 상세정보
     */
    ReservationDto getById(long id);
    // rest api

    /**
     * 예약 등록
     */
    ReservationDto reserve(ReservationInput parameter, String userId);

    /**
     * 예약 삭제
     */
    void delete(Long reservationId, String userId);

    /**
     * 예약 수정 (단, 가게 주인이 예약 승인 및 거절을 아직 하지않고 WATING일 경우)
     */
    ReservationDto update(ReservationInput parameter, Long reservationId, String userId);

    /**
     * 전체 예약 조회 By 점주 마이페이지
     */
    Map<Long, List<ReservationDto>> getReservationByOwnerId(String ownerId);

    /**
     * 가게 별로 예약 조회 By 점주 마이페이지
     */
    List<ReservationDto> getReservationByOwnerIdAndStoreId(String ownerId, Long storeId);

    List<ReservationDto> getReservationByStoreId(Long storeId);

    /**
     * 손님 예약 허용
     */
    ReservationDto approvalReservation(Long reservationId);

    /**
     * 손님 예약 거절
     */
    ReservationDto refuseReservation(Long reservationId);

    /**
     * 전체 예약 조회 By 사용자 마이페이지
     */
    List<ReservationDto> getReservationsByUserId(String userId);

    /**
     * 가게 앞 키오스크
     */
    List<ReservationDto> getReservationByUserIdOnKiosk(String userId, Long storeId);

    /**
     * 승인 예약만 조회
     */

    /**
     * 거부 예약만 조회
     */


    /**
     * 도착 후 키오스크 조작
     */
    ReservationDto arriveOnStore(Long reservationId);
}
