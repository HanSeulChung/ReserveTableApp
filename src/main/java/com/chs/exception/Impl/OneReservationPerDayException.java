package com.chs.exception.Impl;

import com.chs.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class OneReservationPerDayException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 해당 날짜에 다른 시간이 예약되어있습니다. 예약은 인당 하루당 1개만 가능합니다.";
    }
}