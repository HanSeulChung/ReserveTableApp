package com.chs.reservation.entity;

import com.chs.member.owner.entity.Store;
import com.chs.member.user.entity.User;
import com.chs.reservation.dto.ReservationDto;
import com.chs.type.ArriveCode;
import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;
import lombok.*;

import javax.persistence.*;
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

    ReservationCode status; //상태(예약 대기, 예약 승인, 예약 거절)
    UsingCode usingCode;
    ArriveCode arriveCode;

    LocalDateTime regDt;  // 예약을 신청한 시간
    LocalDateTime resDt;  // 이용할 예약 시간
    LocalDateTime arrDt;  // 매장 방문 도착 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    public static Reservation toEntity(ReservationDto reservationDto) {
        return Reservation.builder()
                .regDt(reservationDto.getRegDt())
                .resDt(reservationDto.getResDt())
                .status(reservationDto.getStatus())
                .usingCode(reservationDto.getUsingCode())
                .build();
    }
}
