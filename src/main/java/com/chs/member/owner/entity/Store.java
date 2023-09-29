package com.chs.member.owner.entity;

import com.chs.member.owner.dto.StoreDto;
import com.chs.reservation.entity.Reservation;
import com.chs.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String storeName ;
    private String phone;
    private String addr;
    private String addrDetail;
    private String description;

    private LocalDateTime regDt;
    private LocalDateTime udtDt;

    @JsonIgnoreProperties({"stores"})
    @ManyToOne
    @JoinColumn(name="owner_user_id")
    private Owner owner;

    @OneToMany(mappedBy = "store" , cascade = CascadeType.REMOVE)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    public static Store toEntity(StoreDto storeDto) {
        return Store.builder()
                .storeName(storeDto.getStoreName())
                .phone(storeDto.getPhone())
                .addr(storeDto.getAddr())
                .addrDetail(storeDto.getAddrDetail())
                .description(storeDto.getDescription())
                .regDt(storeDto.getRegDt())
                .build();
    }
}
