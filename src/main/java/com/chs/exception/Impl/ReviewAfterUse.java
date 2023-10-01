package com.chs.exception.Impl;

import com.chs.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ReviewAfterUse extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "사용한 이후에 리뷰를 쓸 수 있습니다.";
    }
}
