package com.chs.member.owner.service;

import com.chs.member.owner.dto.OwnerDto;
import com.chs.member.user.dto.MemberInput;
import com.chs.member.model.Auth;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;

public interface OwnerService extends UserDetailsService {
    /**
     * 회원가입 정보 저장
     * @param member
     * @return
     */

    OwnerDto register(Auth.SignUp member);

    /**
     * login 정보
     * @param member
     * @return
     */

    OwnerDto authenticate(Auth.SignIn member);


//    /**
//     * 입력한 이메일로 비밀번호 초기화 정보를 전송
//     */
//    boolean sendResetPassword(ResetPasswordInput parameter);


    /**
     * 회원 목록 리턴(관리자에서만 사용 가능)
     */
    List<OwnerDto> list(Auth.SignIn member);

    /**
     * 회원 상세 정보
     */
    OwnerDto detail(String userId);

    /**
     * 회원 상태 변경
     */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     */
    boolean updatePassword(String userId, String password);

    /**
     * 회원정보 수정
     */
    OwnerDto updateMember(Auth.SignIn member);

    /**
     * 회원 정보 페이지내 비밀번호 변경 기능
     */
    OwnerDto updateMemberPassword(MemberInput parameter);

    /**
     * 회원을 탈퇴시켜 주는 로직
     */
    OwnerDto withdraw(String userId, String password);

    /**
     * 마지막 로그인 날짜 저장
     */
    void updateLastLoginDt(String userId, LocalDateTime lastLoginDt);
}