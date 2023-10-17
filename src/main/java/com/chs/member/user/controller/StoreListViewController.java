package com.chs.member.user.controller;


import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StoreListViewController {
    private final StoreService storeService;

    @GetMapping(value = {"/store/all","no-auth/store/all"})
    public String viewAllStore(Model model, final Pageable pageable) {
        Page<StoreDto> stores = this.storeService.getAllStore(pageable);

        List<StoreDto> storeDtoList = this.storeService.getAllStore();
        model.addAttribute("list", storeDtoList);
        return "store/list";
    }


    @GetMapping(value = {"/store/detail.do", "/no-auth/store/detail.do"})
    public String detail(Model model, StoreInput parameter) {
        long id = parameter.getId();
        StoreDto existStore = storeService.getById(id);

        model.addAttribute("detail", existStore);

        return "store/detail";
    }

    @GetMapping("/store/search")
    public String searchStore(@RequestParam(name = "query", required = false) String query, Model model) {
        List<StoreDto> storeDtoList;

        if (query != null) {
            String trimmedQuery = query.trim();

            // Try searching by store address
            storeDtoList = storeService.getStoreByStoreAddr(trimmedQuery);

            // If no results found by address, try searching by store name
            if (storeDtoList.isEmpty()) {
                storeDtoList = storeService.getStoreByStoreName(trimmedQuery);
            }
        } else {
            // Invalid request, handle accordingly
            return "redirect:/store/all";
        }

        model.addAttribute("list", storeDtoList);
        return "store/list";
    }
    @GetMapping("/autocomplete")
    @ResponseBody
    public ResponseEntity<List<String>> autocomplete(@RequestParam("query") String query,
                                                     @RequestParam(name = "searchBy", required = false) String searchBy) {
        List<String> autoCompleteResults;
        if ("address".equals(searchBy)) {
            // 가게 주소에서 검색어(query)를 포함하는 가게들을 찾아서 반환
            autoCompleteResults = storeService.getAutoCompleteResultsByAddress(query);
        } else {
            // 기본적으로는 가게 이름에서 검색어(query)를 포함하는 가게들을 찾아서 반환
            autoCompleteResults = storeService.getAutoCompleteResultsByName(query);
        }
        return ResponseEntity.ok(autoCompleteResults);
    }

    @GetMapping("store/search/storename/{storeName}")
    public ResponseEntity<?> searchStoreByName(
            @PathVariable String storeName) {
        var result = this.storeService.getStoreByStoreName(storeName);

        return ResponseEntity.ok(result);
    }

    @GetMapping("store/search/storeaddr/{storeAddr}")
    public ResponseEntity<?> searchStoreByAddr(
            @PathVariable String storeAddr) {
        var result = this.storeService.getStoreByStoreAddr(storeAddr);

        return ResponseEntity.ok(result);
    }
}
