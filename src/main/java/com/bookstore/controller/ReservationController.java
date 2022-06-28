package com.bookstore.controller;

import com.bookstore.dto.request.ReservationRequest;
import com.bookstore.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@CrossOrigin
@RequestMapping("/reservation")
@RequestScope
@RequiredArgsConstructor
@Validated
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping("/student")
    public ResponseEntity<Boolean> reservationForStudent(@RequestBody @Validated ReservationRequest reservationRequest){
        return ResponseEntity.ok(reservationService.reservationForStudent(reservationRequest));
    }
    @PostMapping("/employee")
    public ResponseEntity<Boolean> reservationForEmployee(@RequestBody @Validated ReservationRequest reservationRequest){
        return ResponseEntity.ok(reservationService.reservationForEmployee(reservationRequest));
    }
}

