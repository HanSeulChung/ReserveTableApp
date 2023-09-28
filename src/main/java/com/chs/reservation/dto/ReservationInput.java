package com.chs.reservation.dto;

import com.chs.type.ArriveCode;
import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReservationInput {

    private long storeId;
    private String storeName;
    private LocalDateTime resDt;  // 이용할 예약 시간
    private int people;
}
