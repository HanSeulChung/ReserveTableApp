package com.chs.reservation.entity;

import com.chs.member.owner.entity.Store;
import com.chs.member.user.entity.User;
import com.chs.reservation.dto.ReservationDto;
import com.chs.review.entity.Review;
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
    int storeOriginId;
    String storeName;

    ReservationCode status; //상태(예약 대기, 예약 승인, 예약 거절)
    UsingCode usingCode;
    ArriveCode arriveCode;

    LocalDateTime regDt;  // 예약을 신청한 시간
    LocalDateTime udtDt;
    LocalDateTime resDt;  // 이용할 예약 시간
    LocalDateTime arrDt;  // 매장 방문 도착 시간

    int people;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    @OneToOne(mappedBy = "reservation")
    private Review review;

    public void setUser(User user) {
        this.user = user;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public static Reservation toEntity(ReservationDto reservationDto) {
        return Reservation.builder()
                .storeName(reservationDto.getStoreName())
                .storeOriginId(reservationDto.getStoreOriginId())
                .regDt(reservationDto.getRegDt())
                .resDt(reservationDto.getResDt())
                .status(reservationDto.getStatus())
                .usingCode(reservationDto.getUsingCode())
                .people(reservationDto.getPeople())
                .build();
    }
}
