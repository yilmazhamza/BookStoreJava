package com.bookstore.controller;

import com.bookstore.dto.request.GivebackRequest;
import com.bookstore.dto.request.BasketRequest;
import com.bookstore.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@CrossOrigin
@RequestScope
@RequestMapping("/borrow")
@RequiredArgsConstructor
@Validated
public class BorrowController {

    private final IBookService iBookService;

    @PostMapping("/student")
    public ResponseEntity<List<Boolean>> borrowBookForStudent(@RequestBody @Validated BasketRequest bookRequest){
        return ResponseEntity.ok(iBookService.borrowForStudent(bookRequest));
    }

    @PostMapping("/employee")
    public ResponseEntity<List<Boolean>> borrowBookForEmployee(@RequestBody @Validated BasketRequest bookRequest){
        return ResponseEntity.ok(iBookService.borrowForEmployee(bookRequest));
    }

    @PutMapping("/givebackBookForStudent") //update gibi düşündüm
    public ResponseEntity<Boolean> givebackToBookForStudent(@RequestBody GivebackRequest giveBackRequest){
        return ResponseEntity.ok(iBookService.givebackToBookForStudent(giveBackRequest));
    }

    @PutMapping("/givebackBookForEmployee") //update gibi düşündüm
    public ResponseEntity<Boolean> givebackToBookForEmployee(@RequestBody GivebackRequest giveBackRequest){
        return ResponseEntity.ok(iBookService.givebackToBookForEmployee(giveBackRequest));
    }

}
