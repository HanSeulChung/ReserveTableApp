package com.chs.exception.Impl;

import com.chs.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class InvalidResetPasswordDt extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "유효한 날짜가 아닙니다.";
    }
}

