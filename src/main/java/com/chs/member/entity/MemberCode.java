package com.chs.member.entity;

public interface MemberCode {

    /**
     * 현재 이용중인 상태
     */
    String MEMBER_STATUS_ING = "ING";

    /**
     * 패널티 누적으로 현재 정지된 상태
     */
    String MEMBER_STATUS_STOP = "STOP";

    /**
     * 현재 탈퇴된 회원
     */
    String MEMBER_STATUS_WITHDRAW = "WITHDRAW";

}
