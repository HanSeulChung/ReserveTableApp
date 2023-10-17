package com.chs.reservation.controller;

import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.service.StoreService;
import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.service.ReservationService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ReservationForUserController {

    private final ReservationService reservationService;
    private final StoreService storeService;
    private final TokenAuthenticationProvider tokenProvider;

    @GetMapping("/reserve/{storeId}")
    public String reservePage(@PathVariable Long storeId, Model model) {
        StoreDto storeDto = storeService.getById(storeId);

        model.addAttribute("store", storeDto);
        return "reservation/add"; // Thymeleaf template name
    }

    @PostMapping("/reserve")
    public String reserve(@ModelAttribute ReservationInput request, Model model) {

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String resDtString = String.valueOf(request.getResDt());
            LocalDateTime resDt = LocalDateTime.parse(resDtString, formatter);
            request.setResDt(resDt);
            // 이제 resDt를 사용하여 예약 서비스를 호출할 수 있습니다
            var result = this.reservationService.reserve(request, request.getUserId());

        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "common/error";
        }
        return "redirect:/store/all";
    }

    @GetMapping("/auth/user/reservation/list.do")
    public String list(Model model){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var reservations = this.reservationService.getReservationsByUserId(userId);
        model.addAttribute("reservations", reservations);
        return "reservation/list";
    }

    @GetMapping("/auth/user/reservation/detail.do")
    public String detail(Model model, ReservationDto parameter){
        long id = parameter.getId();
        ReservationDto existReservation = reservationService.getById(id);

        model.addAttribute("detail", existReservation);
        return "reservation/detail";
    }

    @GetMapping("/auth/user/reservation/edit.do")
    public String editform(@ModelAttribute ReservationDto parameter, Model model) {

        long id = parameter.getId();
        ReservationDto existReservation = reservationService.getById(id);

        model.addAttribute("detail", existReservation);
        return "reservation/edit";
    }

    @PostMapping("edit")
    public String edit(@ModelAttribute ReservationInput parameter, Model model) {

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String resDtString = String.valueOf(parameter.getResDt());
            LocalDateTime resDt = LocalDateTime.parse(resDtString, formatter);
            parameter.setResDt(resDt);
            // 이제 resDt를 사용하여 예약 서비스를 호출할 수 있습니다
            var result = this.reservationService.update(parameter, parameter.getId(), parameter.getUserId());

        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "common/error";
        }
        return "redirect:/store/all";
    }
    @PostMapping("/auth/user/reservation/delete.do")
    public String delete(Model model, HttpServletRequest request
            , ReservationInput parameter) {

        try {
            boolean result = reservationService.delete(parameter.getIdList());
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "common/error";
        }
        return "redirect:/auth/user/reservation/list.do";
    }


    /**
     * rest api
     */
//    @PostMapping("/reserve")
//    public ResponseEntity<?> reserve(@RequestBody ReservationInput request,
//                                     @RequestHeader("Authorization") String token) {
//        String userId = tokenProvider.getUserId(token.substring("Bearer ".length()));
//        var result = this.reservationService.reserve(request, userId);
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/get/myreservations")
    public ResponseEntity<?> viewMyreservation( @RequestHeader("Authorization") String token){
        String userId = tokenProvider.getUserId(token.substring("Bearer".length()));
        var result = this.reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(result);

    }
    @DeleteMapping("/delete/reservation")
    public ResponseEntity<?> delete(@RequestParam Long reservationId,
                                    @RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserId(token.substring("Bearer".length()));
        this.reservationService.delete(reservationId, userId);
        return ResponseEntity.ok("해당 예약이 삭제되었습니다.");
    }

    @PostMapping("/update/reservation")
    public ResponseEntity<?> update(@RequestParam Long reservationId,
                                    @RequestBody ReservationInput request,
                                    @RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserId(token.substring("Bearer".length()));
        var result = this.reservationService.update(request, reservationId, userId);
        return ResponseEntity.ok(result);
    }

}
