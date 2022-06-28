package com.bookstore.controller;

import com.bookstore.domain.book.ReservationStatus;
import com.bookstore.exception.ReservationNotFoundException;
import com.bookstore.repository.ReservationRepositoryForEmployee;
import com.bookstore.repository.ReservationRepositoryForStudent;
import com.bookstore.service.impl.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private ReservationRepositoryForStudent reservationRepositoryForStudent;
    private ReservationRepositoryForEmployee reservationRepositoryForEmployee;
    private ReservationService reservationService;
    private ModelMapper modelMapper;

    public AdminController(ReservationRepositoryForStudent reservationRepositoryForStudent, ReservationRepositoryForEmployee reservationRepositoryForEmployee,ReservationService reservationService, ModelMapper modelMapper) {
        this.reservationRepositoryForStudent = reservationRepositoryForStudent;
        this.reservationRepositoryForEmployee = reservationRepositoryForEmployee;
        this.reservationService = reservationService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/student/{id}")
    void adminReservationControlForStudent(@RequestParam Long id, ReservationStatus status) throws ReservationNotFoundException {
        //denemek için her şeyi burada yazacağım
        var reservation = reservationRepositoryForStudent.findById(id);
        if(reservation.isPresent()){
            reservation.get().setReservationStatus(status);
            reservationService.adminReservationControlForStudent(reservation.get());
        }else {
            throw new ReservationNotFoundException("there is no reservation with id: " + id);
        }
    }

    @PutMapping("/employee/{id}")
    void adminReservationControlForEmployee(@RequestParam Long id, ReservationStatus status) throws ReservationNotFoundException {
        var reservation = reservationRepositoryForEmployee.findById(id);
        if(reservation.isPresent()){
            reservation.get().setReservationStatus(status);
            reservationService.adminReservationControlForEmployee(reservation.get());
        }else {
            throw new ReservationNotFoundException("there is no reservation with id: " + id);
        }
    }
}
