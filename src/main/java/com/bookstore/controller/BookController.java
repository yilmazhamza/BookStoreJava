package com.bookstore.controller;

import com.bookstore.dto.request.BookRequest;
import com.bookstore.dto.response.BookGenericResponse;
import com.bookstore.service.IBookService;
import com.bookstore.validation.TcKimlikNo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@CrossOrigin
@RequestScope
@RequestMapping("/books")
@RequiredArgsConstructor
@Validated
public class BookController {

    private final IBookService iBookService;

    @GetMapping("/{identity}")
    public ResponseEntity<BookGenericResponse> findBookById(@PathVariable String identity){
        return ResponseEntity.ok(iBookService.findBookByIdentity(identity));
    }

    @GetMapping
    public ResponseEntity<List<BookGenericResponse>> findAllBooks(){
        return ResponseEntity.ok(iBookService.findAllBooks());
    }

    @PostMapping
    public ResponseEntity<BookGenericResponse> saveBook(@RequestBody @Validated BookRequest bookRequest){
        return ResponseEntity.ok(iBookService.saveBook(bookRequest));
    }


    @PutMapping
    public ResponseEntity<BookGenericResponse> updateBook(@RequestBody BookRequest book){
        return ResponseEntity.ok(iBookService.updateBook(book));
    }

    @DeleteMapping("/{identity}")
    public void deleteBookById(@PathVariable @TcKimlikNo String identity){
         iBookService.deleteBookById(identity);
    }



}
