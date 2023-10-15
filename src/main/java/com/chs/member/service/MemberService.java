package com.chs.member.service;

import com.chs.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService  extends UserDetailsService {

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordInput parameter);

    /**
     * 입력받은 uuid에 대해서 password로 초기화함
     */
    boolean resetPassword(String uuid, String password);

    /**
     * 입력받은 uuid값이 유효한지 확인
     */
    boolean checkResetPassword(String uuid);
}
