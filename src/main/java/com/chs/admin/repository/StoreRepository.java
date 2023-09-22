package com.chs.admin.repository;

import com.chs.admin.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByStoreNameAndAddrDetail(String storeName, String addrDetail);
}
