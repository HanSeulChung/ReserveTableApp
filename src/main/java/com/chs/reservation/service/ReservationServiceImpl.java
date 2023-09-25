package com.chs.reservation.service;


import com.chs.reservation.dto.ReservationDto;
import com.chs.reservation.dto.ReservationInput;
import com.chs.reservation.entity.Reservation;
import com.chs.reservation.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationDto reserve(ReservationInput parameter) {
        return null;
    }

    @Override
    public Page<Reservation> getAllReservations(Pageable pageable) {

        return this.reservationRepository.findAll(pageable);
    }
}
