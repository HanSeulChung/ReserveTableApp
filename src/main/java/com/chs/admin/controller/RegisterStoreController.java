package com.chs.admin.controller;


import com.chs.admin.dto.StoreDto;
import com.chs.admin.dto.StoreInput;
import com.chs.admin.repository.StoreRepository;
import com.chs.admin.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class RegisterStoreController {

    private final StoreService storeService;

    @PostMapping("register/store")
    public ResponseEntity<?> registerStore(@RequestBody StoreInput request) {
        var result = storeService.registerStore(request);
        return ResponseEntity.ok(result);
    }
}
