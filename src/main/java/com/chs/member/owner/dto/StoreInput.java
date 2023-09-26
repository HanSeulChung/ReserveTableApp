package com.chs.member.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StoreInput {
    private String storeName ;
    private String phone;
    private String addr;
    private String addrDetail;
    private String description;
}
