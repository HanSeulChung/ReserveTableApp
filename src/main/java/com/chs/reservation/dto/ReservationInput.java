package com.chs.reservation.dto;

import com.chs.type.ArriveCode;
import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReservationInput {

    private long id;
    private int storeOriginId;
    private String userId;
    private String storeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime resDt;  // 이용할 예약 시간
    private int people;

    //삭제를 위한
    String idList;
}
