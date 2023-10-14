package com.chs.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ResetPasswordInput {
    private String userId;
    private String userName;
    private String userEmail;
}
