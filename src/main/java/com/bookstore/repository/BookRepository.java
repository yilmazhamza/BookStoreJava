package com.bookstore.repository;

import com.bookstore.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String > {

}
