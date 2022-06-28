package com.bookstore.domain.book;


import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @Column(nullable = false,unique = true, updatable = false)
    //@ISBN
    private String isbn;

    @NotBlank
    @Column(nullable = false, unique = true, length = 40)
    private String bookName;

    @NotBlank
    @Column(nullable = false,length = 40)
    private String author;

    @CreatedDate
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    //@Lob
//    @Type(type="org.hibernate.type.BinaryType") -> for postgresql
    @Column(nullable = true,columnDefinition = "longblob")
    private byte[] cover;

    @Column(nullable = false, name = "publish_year")
    @Min(1800)
    @Max(2023)
    private int publishYear;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BookType bookType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BookStatus status;

    @NotNull
    @Column(nullable = false, length = 40)
    private String publisher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    public String getCoverBase64() {
        if (Objects.isNull(cover))
            throw new RuntimeException("Cover is not null !");
        return Base64.encodeBase64String(cover);
    }



}
