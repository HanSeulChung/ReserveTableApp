package com.chs.exception.Impl;

import com.chs.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class EditReservationException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "예약 상태가 대기상태가 아닙니다. 가게에서 승인 및 거절한 예약은 수정할 수 없습니다.";
    }
}
