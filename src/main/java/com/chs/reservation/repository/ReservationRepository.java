package com.chs.reservation.repository;

import com.chs.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUser_UserId(String userUserId);
    List<Reservation> findAllByStore_Id(Long storeId);
    List<Reservation> findAllByStore_IdAndUser_UserId(Long storeId, String userUserId);
}
