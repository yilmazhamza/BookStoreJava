package com.bookstore.repository;

import com.bookstore.domain.book.Book;
import com.bookstore.domain.book.BorrowBookForEmployee;
import com.bookstore.domain.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepositoryForEmployee extends JpaRepository<BorrowBookForEmployee,Long> {
    BorrowBookForEmployee findByBookIsbnAndAndUserId(Book isbn, Employee employee);
}
