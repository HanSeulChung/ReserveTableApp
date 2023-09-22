package com.chs.member.dto;

import com.chs.member.entity.User;
import com.chs.type.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;
    private LocalDateTime lastLoginDt;
    private boolean adminYn;
    private int penalty;

    private MemberStatus status;

    public static UserDto of(User user) {

        return UserDto.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .phone(user.getPhone())
                .regDt(user.getRegDt())
                .udtDt(user.getUdtDt())
                .adminYn(user.isAdminYn())
                .status(user.getStatus())
                .lastLoginDt(user.getLastLoginDt())
                .penalty(user.getPenalty())
                .build();
    }
}
