package com.chs.member.owner.service;

import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.entity.Store;
import com.chs.member.owner.dto.StoreEditInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService {

    // 타임리프용
    /**
     * 자동완성
     */
    List<String> getAutoCompleteResultsByName(String query);

    List<String> getAutoCompleteResultsByAddress(String query);

    /**
     * 가게 등록
     */
    boolean add(StoreInput parameter, String ownerId);

    /**
     * 가게 수정
     */
    boolean edit(StoreInput parameter);

    /**
     * 가게 삭제
     */
    boolean delete(String idList);


    /**
     * 가게 상세정보
     */
    StoreDto getById(long id);

    // REST API용

    /**
     * 가게 등록
     */
    StoreDto registerStore(StoreInput parameter, String ownerId);

    /**
     * 전체 가게 조회
     */
    Page<StoreDto> getAllStore(Pageable pageable);
    List<StoreDto> getAllStore();

    /**
     * 가게 이름으로 가게 조회
     */
    List<StoreDto> getStoreByStoreName(String storeName);

    /**
     * 가게 주소(상세 주소 X)로 가게 조회
     */
    List<StoreDto> getStoreByStoreAddr(String storeAddr);

    /**
     * 가게 점장의 아이디로 가게 조회
     */
    List<StoreDto> getStoreByOwnerId(String ownerId);

    /**
     * 가게 점장의 아이디와 가게 Id로 가게 1개 조회
     */
    StoreDto getStoreByOwnerIdAndStoreId(String ownerId, Long storeId);

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


    List<StoreDto> getAllStores(String ownerId);
}
