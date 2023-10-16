package com.chs.member.owner.controller;


import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.dto.StoreEditInput;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.service.StoreService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StoreCRUDController {

    private final StoreService storeService;
    private final TokenAuthenticationProvider tokenProvider;

    @GetMapping("/auth/owner/store/list.do")
    public String storeManage(Model model){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String ownerId = authentication.getName();
        List<StoreDto> stores = storeService.readStore(ownerId);

        model.addAttribute("authorities", authorities);
        model.addAttribute("list", stores);
        return "store/store_manage";
    }


    @GetMapping(value = {"/auth/owner/store/add.do", "/auth/owner/store/edit.do"})
    public String add(Model model, HttpServletRequest request
            , StoreInput parameter) {

        boolean editMode = request.getRequestURI().contains("/edit.do");
        StoreDto detail = new StoreDto();

        if (editMode) {

            try {
                long id = parameter.getId();
                StoreDto existStore = storeService.getById(id);
                boolean result = storeService.edit(parameter);
                detail = existStore;

            } catch (RuntimeException e) {
                model.addAttribute("message", e.getMessage());
                return "common/error";
            }
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "store/register";
    }


    @PostMapping(value = {"/auth/owner/store/add.do", "/auth/owner/store/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request
//            , MultipartFile file
            , StoreInput parameter) {

//        String saveFilename = "";
//        String urlFilename = "";

//        if (file != null) {
//            String originalFilename = file.getOriginalFilename();
//
//            String baseLocalPath = "C:/lecture/fastlms3/files";
//            String baseUrlPath = "/files";
//
//            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);
//
//            saveFilename = arrFilename[0];
//            urlFilename = arrFilename[1];
//
//            try {
//                File newFile = new File(saveFilename);
//                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
//            } catch (IOException e) {
//                log.info("############################ - 1");
//                log.info(e.getMessage());
//            }
//        }
//
//        parameter.setFilename(saveFilename);
//        parameter.setUrlFilename(urlFilename);

        boolean editMode = request.getRequestURI().contains("/edit.do");
        String ownerId = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            if (editMode) {
                long id = parameter.getId();
                StoreDto existStore = storeService.getById(id);
                boolean result = storeService.edit(parameter);
            } else {
                boolean result = storeService.add(parameter, ownerId);
            }
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "common/error";
        }
        return "redirect:/auth/owner/store/list.do";
    }


    @PostMapping("/auth/owner/store/delete.do")
    public String delete(Model model, HttpServletRequest request
            , StoreInput parameter) {

        boolean result = storeService.delete(parameter.getIdList());

        return "redirect:/auth/owner/store/list.do";
    }




    /**
     * RESTAPI
     */
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
