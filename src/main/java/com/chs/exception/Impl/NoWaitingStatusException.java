package com.chs.exception.Impl;

import com.chs.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NoWaitingStatusException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "Waiting 상태만 승인 혹은 거절이 가능합니다.";
    }
}