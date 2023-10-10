package com.chs.member.user.service;

import com.chs.member.user.dto.UserDto;
import com.chs.member.user.entity.User;
import com.chs.member.model.Auth;
import com.chs.member.user.dto.MemberInput;
import com.chs.type.MemberStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService  extends UserDetailsService {
    /**
     * 회원가입 정보 저장
     * @param member
     * @return
     */

    boolean register(Auth.SignUp member);

    /**
     * login 정보
     * @param member
     * @return
     */

    UserDto authenticate(Auth.SignIn member);


//    /**
//     * 입력한 이메일로 비밀번호 초기화 정보를 전송
//     */
//    boolean sendResetPassword(ResetPasswordInput parameter);


    /**
     * 회원 목록 리턴(관리자에서만 사용 가능)
     */
    List<UserDto> list(Auth.SignIn member);

    /**
     * 회원 상세 정보
     */
    UserDto detail(String userId);

    /**
     * 회원 상태 변경
     */
    boolean updateStatus(String userId, MemberStatus userStatus);

    /**
     * 회원 비밀번호 초기화
     */
    boolean updatePassword(String userId, String password);

    /**
     * 회원정보 수정
     */
    UserDto updateMember(String userId, Auth.SignEdit member);

    /**
     * 회원 정보 페이지내 비밀번호 변경 기능
     */
    UserDto updateMemberPassword(MemberInput parameter);

    /**
     * 회원을 탈퇴시켜 주는 로직
     */
    UserDto withdraw(String userId, String password);

    /**
     * 마지막 로그인 날짜 저장
     */
    void updateLastLoginDt(String userId, LocalDateTime lastLoginDt);
}

