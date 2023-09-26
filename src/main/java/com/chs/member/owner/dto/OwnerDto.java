package com.chs.member.owner.dto;

import com.chs.member.owner.entity.Owner;
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
public class OwnerDto {
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;
    private boolean adminYn;

    private MemberStatus status;  //이용가능한상태, 정지상태
    private LocalDateTime lastLoginDt;

    public static OwnerDto of(Owner owner) {

        return OwnerDto.builder()
                .userId(owner.getUserId())
                .userName(owner.getUsername())
                .phone(owner.getPhone())
                .regDt(owner.getRegDt())
                .udtDt(owner.getUdtDt())
                .adminYn(owner.isAdminYn())
                .status(owner.getStatus())
                .lastLoginDt(owner.getLastLoginDt())
                .build();
    }
}
