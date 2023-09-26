package com.chs.reservation.repository;

import com.chs.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(String userId);

    List<Reservation> findAllByStoreId(Long storeId);

    Optional<Reservation> findByStoreIdAndUserId(Long storeId, String userId);
}
