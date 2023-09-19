package com.chs.member.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Member implements MemberCode{
    @Id
    private String userId;
    private String userName;
    private String phone;
    private String userPw;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;
    private int penalty;
    private boolean adminYn;

    private String userStatus;//이용가능한상태, 정지상태
    private LocalDateTime lastLoginDt;
}
