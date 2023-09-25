package com.chs.admin.controller;


import com.chs.admin.dto.StoreEditInput;
import com.chs.admin.dto.StoreInput;
import com.chs.admin.service.StoreService;
import com.chs.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class StoreCRUDController {

    private final StoreService storeService;
    private final TokenProvider tokenProvider;

    @PostMapping("register/store")
    public ResponseEntity<?> registerStore(@RequestBody StoreInput request,
                                           @RequestHeader("Authorization") String token) {
        String ownerId = tokenProvider.getUserId(token.substring("Bearer ".length()));
        var result = storeService.registerStore(request, ownerId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("delete/store")
    public ResponseEntity<?> deleteStore(@RequestParam Long storeId, @RequestParam String storeName) {
        storeService.deleteStore(storeId, storeName);
        return ResponseEntity.ok("해당 매장이 삭제되었습니다.");
    }

    @PostMapping("update/store")
    public ResponseEntity<?> updateStore(@RequestBody StoreEditInput request) {
        var result = storeService.updateStore(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("read/store")
    public ResponseEntity<?> readStore(@RequestParam String ownerId) {
        var result = storeService.readStore(ownerId);
        return ResponseEntity.ok(result);
    }

}
