package com.chs.member.model;

import com.chs.member.owner.entity.Owner;
import com.chs.member.user.entity.User;
import com.chs.type.MemberStatus;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class Auth {

    @Data
    public static class SignIn {
        @NotBlank(message = "로그인 시 아이디 입력이 필수입니다.")
        private String userId;
        @Size(max = 4)
        @NotBlank(message = "최소 4자 이상 비밀번호를 입력해야합니다.")
        private String password;
    }

    @Data
    public static class SignUp {
        @NotBlank(message = "회원 가입 시 아이디 입력이 필수입니다.")
        private String userId;

        @Size(min = 4)
        @NotBlank(message = "최소 4자 이상 비밀번호를 등록해야합니다.")
        private String password;

        @NotBlank(message = "회원 가입 시 회원 이름이 필수입니다.")
        private String userName;

        @Email
        @NotBlank(message = "회원가입 시 이메일 입력은 필수입니다.")
        private String email;

        @Pattern(regexp = "01[016-9]-\\d{3,4}-\\d{4}", message = "유효하지 않은 핸드폰 번호입니다.")
        @NotBlank(message = "회원가입 시 핸드폰 번호 입력은 필수입니다.")
        private String phone;


        public User toUserEntity() {
            return User.builder()
                    .userId(this.userId)
                    .userName(this.userName)
                    .email(this.email)
                    .phone(this.phone)
                    .password(this.password)
                    .adminYn(false)
                    .regDt(LocalDateTime.now())
                    .penalty(0)
                    .status(MemberStatus.ING)
                    .build();
        }

        public Owner toOwnerEntity() {
            return Owner.builder()
                    .userId(this.userId)
                    .userName(this.userName)
                    .email(this.email)
                    .phone(this.phone)
                    .password(this.password)
                    .adminYn(true)
                    .regDt(LocalDateTime.now())
                    .status(MemberStatus.ING)
                    .build();
        }
    }

    @Data
    public static class SignEdit {
        @Pattern(regexp = "01[016-9]-\\d{3,4}-\\d{4}", message = "유효하지 않은 핸드폰 번호입니다.")
        @NotBlank(message = "회원가입 시 핸드폰 번호 입력은 필수입니다.")
        private String phone;
    }

}
