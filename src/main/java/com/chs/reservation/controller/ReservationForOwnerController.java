package com.chs.reservation.controller;


import com.chs.member.owner.service.StoreService;
import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationForOwnerController {
    private final ReservationService reservationService;
    private final StoreService storeService;

//    @GetMapping("{ownerId}/all")
//    public ResponseEntity<?> viewAllReservation(@PathVariable String ownerId) {
//        List<StoreDto> storeByOwnerId = this.storeService.getStoreByOwnerId(ownerId);
//        Map<Long, List<ReservationDto>> reservationMapByOwnerId = this.reservationService.getReservationByOwnerId(ownerId);
//        return ResponseEntity.ok(reservationMapByOwnerId);
//    }

    // 타임리프용
    @GetMapping("/auth/owner/reservation")
    public String listStores(Model model) {
        String ownerId = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("stores", storeService.getAllStores(ownerId)); // 가게 목록을 가져오는 서비스 메서드 호출
        return "reservation/owner/lists"; // HTML 템플릿 이름 반환
    }


    @PostMapping("/auth/owner/reservation/approve/{reservationId}")
    public String approve(@PathVariable Long reservationId, Model model) {
        try {
            var result = reservationService.approvalReservation(reservationId);
            int storeId = result.getStoreOriginId();
            return "redirect:/auth/owner/reservation/store/" + storeId;
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "common/error";
        }
    }

    @PostMapping("/auth/owner/reservation/refuse/{reservationId}")
    public String refuse(@PathVariable Long reservationId, Model model) {
        try {
            var result = reservationService.refuseReservation(reservationId);
            int storeId = result.getStoreOriginId();
            return "redirect:/auth/owner/reservation/store/" + storeId;
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "common/error";
        }
    }

    @GetMapping("/auth/owner/reservation/store/{storeId}")
    public String listReservationsForStore(@PathVariable Long storeId, Model model) {
        // 가게에 관련된 예약 목록을 가져오는 서비스 메서드 호출
        List<ReservationDto> reservations = reservationService.getReservationByStoreId(storeId);
        String storeName = storeService.getById(storeId).getStoreName(); // 가게 이름 가져오기
        model.addAttribute("reservations", reservations);
        model.addAttribute("storeName", storeName);
        return "reservation/owner/list"; // HTML 템플릿 이름 반환
    }
    @GetMapping("{ownerId}/{storeId}")
    public ResponseEntity<?> viewReservation(@PathVariable String ownerId,
                                             @PathVariable Long storeId) {
        var result = this.reservationService.getReservationByOwnerIdAndStoreId(ownerId, storeId);
        return ResponseEntity.ok(result);
    }

//    @PostMapping("/auth/owner/reservation/approve")
//    public ResponseEntity<?> reservationApproval(@RequestParam Long reservationId) {
//        var result = reservationService.approvalReservation(reservationId);
//        return ResponseEntity.ok(result);
//    }
//
//    @PostMapping("/auth/owner/reservation/refuse")
//    public ResponseEntity<?> reservationRefuse(@RequestParam Long reservationId) {
//        var result = reservationService.refuseReservation(reservationId);
//        return ResponseEntity.ok(result);
//    }
}
