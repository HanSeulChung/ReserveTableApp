package com.chs.member.owner.entity;

import com.chs.member.owner.dto.StoreDto;
import com.chs.reservation.entity.Reservation;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_user_id")
    private Owner owner;

    @OneToMany(mappedBy = "store" , cascade = CascadeType.REMOVE)
    private List<Reservation> reservations;

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
