package com.chs.member.owner.repository;

import com.chs.member.owner.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByStoreNameAndAddrDetail(String storeName, String addrDetail);
    boolean existsById(Long storeId);
    List<Store> findAllByStoreName(String storeName);
    List<Store> findAllByAddr(String storeAddr);
    List<Store> findAllByOwnerId(String ownerId);
    @Transactional
    void deleteAllByIdAndStoreName(Long storeId, String storeName);
}
