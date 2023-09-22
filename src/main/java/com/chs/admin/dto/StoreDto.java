package com.chs.admin.dto;

import com.chs.admin.entity.Owner;
import com.chs.admin.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StoreDto {
    private String storeName ;
    private String phone;
    private String addr;
    private String addrDetail;
    private String description;

    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    public static StoreDto of(Store store) {

        return StoreDto.builder()
                .storeName(store.getStoreName())
                .phone(store.getPhone())
                .addr(store.getAddr())
                .addrDetail(store.getAddrDetail())
                .description(store.getDescription())
                .regDt(store.getRegDt())
                .udtDt(store.getUdtDt())
                .build();
    }

    public static StoreDto fromInput(StoreInput storeInput) {
        return StoreDto.builder()
                .storeName(storeInput.getStoreName())
                .phone(storeInput.getPhone())
                .addr(storeInput.getAddr())
                .addrDetail(storeInput.getAddrDetail())
                .description(storeInput.getDescription())
                .regDt(LocalDateTime.now())
                .build();
    }
}
