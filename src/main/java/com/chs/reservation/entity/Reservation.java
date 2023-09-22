package com.chs.reservation.entity;

import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long storeId;
    String userId;


    ReservationCode status; //상태(예약 승인, 예약 거절)
    UsingCode usingCode;

    LocalDateTime regDt;  // 예약을 신청한 시간
    LocalDateTime resDt;  // 이용할 예약 시간
    LocalDateTime arrDt;  // 매장 방문 도착 시간

}
