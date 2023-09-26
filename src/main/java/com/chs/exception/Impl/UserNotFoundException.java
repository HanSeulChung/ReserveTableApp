package com.chs.exception.Impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    String username;
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }


    public UserNotFoundException(String msg) {
        super(msg + " 사용자가 존재하지 않습니다. 올바른 로그인 후 접근 권한이 필요합니다.");
    }
}
