package com.bookstore.repository;

import com.bookstore.domain.book.Book;
import com.bookstore.domain.book.BorrowBookForStudent;
import com.bookstore.domain.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepositoryForStudent extends JpaRepository<BorrowBookForStudent,Long> {
    BorrowBookForStudent findByBookIsbnAndAndUserId(Book isbn, Student student);
}
