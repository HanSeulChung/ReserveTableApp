package com.chs.reservation.service;


import com.chs.exception.Impl.*;
import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.entity.Store;
import com.chs.member.owner.repository.StoreRepository;
import com.chs.member.owner.service.StoreService;
import com.chs.member.user.entity.User;
import com.chs.member.user.repository.UserRepository;
import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.entity.Reservation;
import com.chs.reservation.repository.ReservationRepository;
import com.chs.type.ArriveCode;
import com.chs.type.ReservationCode;
import com.chs.type.UsingCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final StoreService storeService;

    @Override
    public ReservationDto reserve(ReservationInput parameter, String userId) {

        User user = userRepository.findByUserId(userId)
                        .orElseThrow(() -> new NoUserIdException());
        Store store = storeRepository.findById(parameter.getStoreId())
                        .orElseThrow(() -> new NoStoreException());

        validateReserve(parameter, userId);
        Reservation reservationSave = Reservation.toEntity(ReservationDto.fromInput(parameter));
        reservationSave.setUser(user);
        reservationSave.setStore(store);
        reservationRepository.save(reservationSave);

        return ReservationDto.of(reservationSave);
    }

    private void validateReserve(ReservationInput parameter, String userId) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime resDtInUTC = parameter.getResDt().atZone(utcZone);
        LocalDateTime nowTime = LocalDateTime.now(utcZone);
        if (nowTime.isAfter(resDtInUTC.toLocalDateTime())) {
            throw new InCorrectReservationException();
        }

        boolean myExists = reservationRepository.existsByUser_UserIdAndResDt(userId, parameter.getResDt());
        if (myExists) {
            throw new AlreadyMyReservationException();
        }

        boolean reservationExist = reservationRepository.existsByStore_IdAndResDt(parameter.getStoreId(), parameter.getResDt());
        if (reservationExist) {
            throw new AlreadyReservationException();
        }

        List<Reservation> allByUserUserId = reservationRepository.findAllByUser_UserId(userId);
        for(Reservation x : allByUserUserId) {
            if (x.getResDt().toLocalDate().equals(parameter.getResDt().toLocalDate())){
                throw new OneReservationPerDayException();
            }
        }

    }

    @Override
    public void delete(Long reservationId, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoUserIdException());
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationException());

        if (!reservation.getUser().getUserId().equals(userId)) {
            new NoReservationFromUserIdException();
        }

        reservationRepository.deleteById(reservationId);
    }

    @Override
    public ReservationDto update(ReservationInput parameter, Long reservationId, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoUserIdException());
        Store store = storeRepository.findById(parameter.getStoreId())
                .orElseThrow(() -> new NoStoreException());

        Reservation reservationForEdit = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationException());

        if (!store.getId().equals(reservationForEdit.getStore().getId())) {
            throw new NoReservationException();
        }
        validateReserve(parameter, userId);

        if (reservationForEdit.getStatus() != ReservationCode.WAITING) {
            throw new EditReservationException();
        }


        return ReservationDto.of(reservationRepository.save(
                Reservation.builder()
                        .id(reservationForEdit.getId())
                        .status(reservationForEdit.getStatus())
                        .usingCode(reservationForEdit.getUsingCode())
                        .resDt(parameter.getResDt())
                        .people(parameter.getPeople())
                        .user(reservationForEdit.getUser())
                        .udtDt(LocalDateTime.now())
                        .store(reservationForEdit.getStore())
                        .build()
        ));
    }

    //    @Override
//    public Page<Reservation> getAllReservations(Pageable pageable) {
//
//        return this.reservationRepository.findAll(pageable);
//    }


    @Override
    public Map<Long, List<ReservationDto>> getReservationByOwnerId(String ownerId) {
        List<StoreDto> storeByOwnerId = this.storeService.getStoreByOwnerId(ownerId);
        Map<Long, List<ReservationDto>> reservationMapByOwnerId = new HashMap<>();

        for (int i = 0; i < storeByOwnerId.size(); i++) {
            reservationMapByOwnerId.put(storeByOwnerId.get(i).getStoreId() ,ReservationDto.of(reservationRepository.findAllByStore_Id(storeByOwnerId.get(i).getStoreId())));
        }

        return reservationMapByOwnerId;
    }

    @Override
    public List<ReservationDto> getReservationByOwnerIdAndStoreId(String ownerId, Long storeId) {

        storeRepository.findByIdAndOwner_UserId(storeId, ownerId)
                .orElseThrow(() -> new NoStoreException());

        List<Store> stores = storeRepository.findAllByOwner_UserId(ownerId);

        if (stores.size() == 0) {
            throw new NoReservationException();
        }

        var result = reservationRepository.findAllByStore_Id(storeId);

        return ReservationDto.of(result);
    }

    @Override
    public List<ReservationDto> getReservationByStoreId(Long storeId) {
        var result = reservationRepository.findAllByStore_Id(storeId);
        if (result == null) {
            throw new NoStoreException();
        }
        return ReservationDto.of(result);
    }

    @Override
    public ReservationDto approvalReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationException());

        Reservation saveReservation = reservationRepository.save(
                Reservation.builder()
                        .id(reservation.getId())
                        .regDt(reservation.getRegDt())
                        .resDt(reservation.getResDt())
                        .status(ReservationCode.APPROVE)
                        .usingCode(reservation.getUsingCode())
                        .arriveCode(reservation.getArriveCode())
                        .arrDt(reservation.getArrDt())
                        .build()
        );
        return ReservationDto.of(saveReservation);
    }

    @Override
    public ReservationDto refuseReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationException());

        Reservation saveReservation = reservationRepository.save(
                Reservation.builder()
                        .id(reservation.getId())
                        .regDt(reservation.getRegDt())
                        .resDt(reservation.getResDt())
                        .status(ReservationCode.REFUSE)
                        .usingCode(reservation.getUsingCode())
                        .arriveCode(reservation.getArriveCode())
                        .arrDt(reservation.getArrDt())
                        .build()
        );
        return ReservationDto.of(saveReservation);
    }

    @Override
    public List<ReservationDto> getReservationsByUserId(String userId) {
        var result = reservationRepository.findAllByUser_UserId(userId);
        return ReservationDto.of(result);
    }

    @Override
    public List<ReservationDto> getReservationByUserIdOnKiosk(String userId, Long storeId) {
        var result = reservationRepository.findAllByStore_IdAndUser_UserId(storeId, userId);

        if (result == null) {
            throw new NoReservationFromUserIdException();
        }

        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation x : result) {
            if (x.getStatus().equals(ReservationCode.APPROVE)) {
                reservationDtoList.add(ReservationDto.of(x));
            }
        }

        if (reservationDtoList.size() == 0) {
            throw new NoApprovalReservationUserException();
        }

        return reservationDtoList;
    }




    @Override
    public ReservationDto arriveOnStore(Long reservationId) {
        var result = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoReservationFromUserIdException());

        LocalDateTime nowTime = LocalDateTime.now();

        if (result.getResDt().toLocalDate().isAfter(nowTime.toLocalDate())) {
            throw new EarlydayThanReservationException();
        }

        if (result.getResDt().toLocalDate().isBefore(nowTime.toLocalDate())) {
            throw new AfterdayThanReservationException();
        }

        LocalTime arrivalTime = nowTime.toLocalTime();
        LocalTime reservationTime = result.getResDt().toLocalTime();

        if (Duration.between(arrivalTime, reservationTime).toMinutes() < 10) {
            throw new NotBefore10minuteException();
        }

        Reservation reservation = result.builder()
                .id(result.getId())
                .arrDt(nowTime)
                .usingCode(UsingCode.COMPLETE)
                .regDt(result.getRegDt())
                .resDt(result.getResDt())
                .arriveCode(ArriveCode.CORRECT_ARRIVAL)
                .status(result.getStatus())
                .build();
        reservationRepository.save(reservation);

        return ReservationDto.of(reservation);
    }
}
