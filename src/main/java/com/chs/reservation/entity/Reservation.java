package com.chs.reservation.entity;

import com.chs.type.ArriveCode;
import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "RESERVATION")
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long storeId;
    String userId;
    String userPhone;

    ReservationCode status; //상태(예약 대기, 예약 승인, 예약 거절)
    UsingCode usingCode;
    ArriveCode arriveCode;

    LocalDateTime regDt;  // 예약을 신청한 시간
    LocalDateTime resDt;  // 이용할 예약 시간
    LocalDateTime arrDt;  // 매장 방문 도착 시간

}
