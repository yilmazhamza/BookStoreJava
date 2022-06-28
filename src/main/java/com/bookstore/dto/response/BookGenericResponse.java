package com.bookstore.dto.response;

import com.bookstore.domain.book.BookStatus;
import com.bookstore.domain.book.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookGenericResponse {

    private String isbn;
    private String bookName;
    private String author;
    private String cover;
    private int publishYear;
    private BookType bookType;
    private BookStatus status;
    private String publisher;


}
