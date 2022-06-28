package com.bookstore.exception.handling;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.BookExistException;
import com.bookstore.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(value = { BookNotFoundException.class, BookExistException.class, UserNotFoundException.class})
    protected ResponseEntity<String> handleConflict(RuntimeException ex) {
        String bodyOfResponse = ex.getMessage() ;
        return new ResponseEntity(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }


}
