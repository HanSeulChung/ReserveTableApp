package com.chs.member.owner.service;

import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.entity.Store;
import com.chs.member.owner.dto.StoreEditInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {

    /**
     * 가게 등록
     */
    StoreDto registerStore(StoreInput parameter, String ownerId);

    /**
     * 전체 가게 조회
     */
    Page<Store> getAllStore(Pageable pageable);

    /**
     * 가게 이름으로 가게 조회
     */
    List<StoreDto> getStoreByStoreName(String storeName);

    /**
     * 가게 주소(상세 주소 X)로 가게 조회
     */

    List<StoreDto> getStoreByStoreAddr(String storeAddr);

    /**
     * 가게 삭제
     */
    void deleteStore(Long storeId, String storeName, String ownerId);

    /**
     * 가게 수정
     */
    StoreDto updateStore(StoreEditInput storeInput, String ownerId);

    /**
     * 가게 주인이 등록한 가게 조회(auth/owner 기능)
     */
    List<StoreDto> readStore(String ownerId);


}
