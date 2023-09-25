package com.chs.admin.entity;

import com.chs.admin.dto.StoreDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "STORE")
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerId;

    private String storeName ;
    private String phone;
    private String addr;
    private String addrDetail;
    private String description;

    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    public static Store toEntity(StoreDto storeDto) {
        return Store.builder()
                .ownerId(storeDto.getOwnerId())
                .storeName(storeDto.getStoreName())
                .phone(storeDto.getPhone())
                .addr(storeDto.getAddr())
                .addrDetail(storeDto.getAddrDetail())
                .description(storeDto.getDescription())
                .regDt(storeDto.getRegDt())
                .build();
    }
}
