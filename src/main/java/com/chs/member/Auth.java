package com.chs.member;

import com.chs.admin.entity.Owner;
import com.chs.member.entity.Member;
import com.chs.member.entity.User;
import com.chs.type.MemberStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class Auth {

    @Data
    public static class SignIn {
        private String username;
        private String password;
    }

    @Data
    public static class SignUp {
        private String username;
        private String phone;
        private String password;
        private boolean adminYn;

        public User toUserEntity() {
            return User.builder()
                    .name(this.username)
                    .phone(this.phone)
                    .password(this.password)
                    .adminYn(false)
                    .regDt(LocalDateTime.now())
                    .adminYn(false)
                    .penalty(0)
                    .status(MemberStatus.ING)
                    .build();
        }

        public Owner toOwnerEntity() {
            return Owner.builder()
                    .name(this.username)
                    .phone(this.phone)
                    .password(this.password)
                    .adminYn(false)
                    .regDt(LocalDateTime.now())
                    .adminYn(adminYn)
                    .status(MemberStatus.ING)
                    .build();
        }
    }
}
