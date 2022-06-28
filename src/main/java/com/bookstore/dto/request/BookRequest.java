package com.bookstore.dto.request;


import com.bookstore.domain.book.BookStatus;
import com.bookstore.domain.book.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @NotBlank
    private String isbn;
    @NotBlank
    private String bookName;
    @NotBlank
    private String author;
    @NotBlank
    private String cover;
    @NotNull
    private int publishYear;

    @NotNull
    private BookType bookType;

    @NotNull
    private BookStatus status;

    @NotBlank
    private String publisher;

    public byte[] getBase64Cover() {
        return Base64.decodeBase64(cover);
    }

}
