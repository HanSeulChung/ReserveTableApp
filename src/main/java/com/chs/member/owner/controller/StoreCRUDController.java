package com.chs.member.owner.controller;


import com.chs.member.owner.dto.StoreEditInput;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.service.StoreService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/auth/owner")
@RequiredArgsConstructor
public class StoreCRUDController {

    private final StoreService storeService;
    private final TokenAuthenticationProvider tokenProvider;

    @GetMapping("/store")
    public String storeManage(){

        return "store/store_manage";
    }

    @PostMapping("register/store")
    public ResponseEntity<?> registerStore(@RequestBody StoreInput request,
                                           @RequestHeader("Authorization") String token) {
        String ownerId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = storeService.registerStore(request, ownerId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("delete/store")
    public ResponseEntity<?> deleteStore(@RequestParam Long storeId,
                                         @RequestParam String storeName,
                                         @RequestHeader("Authorization") String token) {
        String ownerId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        storeService.deleteStore(storeId, storeName, ownerId);
        return ResponseEntity.ok("해당 매장이 삭제되었습니다.");
    }

    @PostMapping("update/store")
    public ResponseEntity<?> updateStore(@RequestBody StoreEditInput request,
                                         @RequestHeader("Authorization") String token) {
        String ownerId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = storeService.updateStore(request, ownerId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("read/store")
    public ResponseEntity<?> readStore(@RequestParam String ownerId) {
        var result = storeService.readStore(ownerId);
        return ResponseEntity.ok(result);
    }

}
