package com.chs.member.controller;


import com.chs.admin.entity.Store;
import com.chs.admin.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreListViewController {
    private final StoreService storeService;
    @GetMapping("/all")
    public ResponseEntity<?> viewAllStore(final Pageable pageable) {
        Page<Store> stores = this.storeService.getAllStore(pageable);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/search/storename/{storeName}")
    public ResponseEntity<?> searchStoreByName(
            @PathVariable String storeName) {
        var result = this.storeService.getStoreByStoreName(storeName);

        return ResponseEntity.ok(result);
    }

    @GetMapping("search/storeaddr/{storeAddr}")
    public ResponseEntity<?> searchStoreByAddr(
            @PathVariable String storeAddr) {
        var result = this.storeService.getStoreByStoreAddr(storeAddr);

        return ResponseEntity.ok(result);
    }
}
