package com.chs.admin.dto;

import com.chs.admin.entity.Owner;
import com.chs.admin.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StoreDto {
    private Long storeId;
    private String ownerId;
    private String storeName ;
    private String phone;
    private String addr;
    private String addrDetail;
    private String description;

    private LocalDateTime regDt;
    private LocalDateTime udtDt;


    public static List<StoreDto> of(List<Store> stores) {

        if (stores != null) {
            List<StoreDto> storeDtoList = new ArrayList<>();
            for(Store x : stores) {
                storeDtoList.add(of(x));
            }
            return storeDtoList;
        }

        return null;
    }

    public static StoreDto of(Store store) {

        return StoreDto.builder()
                .ownerId(store.getOwnerId())
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .phone(store.getPhone())
                .addr(store.getAddr())
                .addrDetail(store.getAddrDetail())
                .description(store.getDescription())
                .regDt(store.getRegDt())
                .udtDt(store.getUdtDt())
                .build();
    }

    public static StoreDto fromInput(StoreInput storeInput, String ownerId) {
        return StoreDto.builder()
                .ownerId(ownerId)
                .storeName(storeInput.getStoreName())
                .phone(storeInput.getPhone())
                .addr(storeInput.getAddr())
                .addrDetail(storeInput.getAddrDetail())
                .description(storeInput.getDescription())
                .regDt(LocalDateTime.now())
                .build();
    }

//    public static StoreDto fromEditInput(StoreEditInput storeEditInput) {
//        return StoreDto.builder()
//                .storeName()
//                .build();
//    }
}
