package com.bookstore.service;

import com.bookstore.dto.request.ReservationRequest;

public interface IReservationService {

    Boolean reservationForStudent(ReservationRequest reservationRequest);

    Boolean reservationForEmployee(ReservationRequest reservationRequest);
}
