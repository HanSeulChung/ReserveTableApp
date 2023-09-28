package com.chs.reservation.repository;

import com.chs.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUser_UserId(String userUserId);
    List<Reservation> findAllByStore_Id(Long storeId);
    List<Reservation> findAllByStore_IdAndUser_UserId(Long storeId, String userUserId);

    boolean existsByUser_UserIdAndResDt(String userUserId, LocalDateTime resDt);
    boolean existsByStore_IdAndResDt(Long storeId, LocalDateTime resDt);

    @Transactional
    void deleteById(Long reservationId);
}
