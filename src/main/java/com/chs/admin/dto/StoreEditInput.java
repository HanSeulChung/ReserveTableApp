package com.chs.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StoreEditInput {
    private Long storeId;
    private String storeName ;
    private String phone;
    private String addr;
    private String addrDetail;
    private String description;
}
