package com.chs.reservation.dto;

import com.chs.reservation.entity.Reservation;
import com.chs.type.ArriveCode;
import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReservationDto {

    private long reservationId;
    private long storeId;
    private String userId;
    private String userPhone;
    private int people;

    private ReservationCode status; //상태(예약 대기, 예약 승인, 예약 거절)
    private UsingCode usingCode;
    private ArriveCode arriveCode;

    private LocalDateTime regDt;  // 예약을 신청한 시간
    private LocalDateTime resDt;  // 이용할 예약 시간
    private LocalDateTime arrDt;  // 매장 방문 도착 시간


    public static List<ReservationDto> of(List<Reservation> reservations) {

        if (reservations != null) {
            List<ReservationDto> reservationDtoList = new ArrayList<>();
            for(Reservation x : reservations) {
                reservationDtoList.add(of(x));
            }
            return reservationDtoList;
        }

        return null;
    }

    public static ReservationDto of(Reservation reservation) {

        return ReservationDto.builder()
                .reservationId(reservation.getId())
                .userId(reservation.getUser().getUserId())
                .userPhone(reservation.getUser().getPhone())
                .storeId(reservation.getStore().getId())
                .status(reservation.getStatus())
                .usingCode(reservation.getUsingCode())
                .arriveCode(reservation.getArriveCode())
                .regDt(reservation.getRegDt())
                .resDt(reservation.getResDt())
                .arrDt(reservation.getArrDt())
                .people(reservation.getPeople())
                .build();
    }

    public static ReservationDto fromInput(ReservationInput reservationInput) {
        return ReservationDto.builder()
                .storeId(reservationInput.getStoreId())
                .usingCode(UsingCode.RESERVATION)
                .status(ReservationCode.WAITING)
                .regDt(LocalDateTime.now())
                .resDt(reservationInput.getResDt())
                .people(reservationInput.getPeople())
                .build();
    }
}
