package com.bookstore.service;

import com.bookstore.dto.request.GivebackRequest;
import com.bookstore.dto.request.BasketRequest;
import com.bookstore.dto.request.BookRequest;
import com.bookstore.dto.response.BookGenericResponse;

import java.util.List;

public interface IBookService {
    BookGenericResponse saveBook(BookRequest bookRequest);
    BookGenericResponse updateBook(BookRequest book);
    BookGenericResponse findBookByIdentity(String identity);
    List<BookGenericResponse> findAllBooks();
    void deleteBookById(String identity);

    List<Boolean> borrowForStudent(BasketRequest bookRequest);
    List<Boolean> borrowForEmployee(BasketRequest bookRequest);

    Boolean givebackToBookForStudent(GivebackRequest giveBackRequest);
    Boolean givebackToBookForEmployee(GivebackRequest giveBackRequest);


}
