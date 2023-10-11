package com.chs.member.user.controller;


import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreListViewController {
    private final StoreService storeService;
    @GetMapping("/all")
    public String viewAllStore(Model model, final Pageable pageable) {
        Page<StoreDto> stores = this.storeService.getAllStore(pageable);

        List<StoreDto> storeDtoList = this.storeService.getAllStore();
        model.addAttribute("list", storeDtoList);
        return "store/all";
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
