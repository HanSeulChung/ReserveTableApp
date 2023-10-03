package com.chs.exception.Impl;

import com.chs.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class AlreadyExistEmailException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 존재하는 사용자 이메일 입니다.";
    }
}

