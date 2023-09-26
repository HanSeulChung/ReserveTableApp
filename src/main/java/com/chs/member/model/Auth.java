package com.chs.member.model;

import com.chs.member.owner.entity.Owner;
import com.chs.member.user.entity.User;
import com.chs.type.MemberStatus;
import lombok.Data;

import java.time.LocalDateTime;

public class Auth {

    @Data
    public static class SignIn {
        private String userId;
        private String password;
    }

    @Data
    public static class SignUp {
        private String userId;
        private String userName;
        private String phone;
        private String password;

        public User toUserEntity() {
            return User.builder()
                    .userId(this.userId)
                    .userName(this.userName)
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
                    .phone(this.phone)
                    .password(this.password)
                    .adminYn(true)
                    .regDt(LocalDateTime.now())
                    .status(MemberStatus.ING)
                    .build();
        }
    }
}
