package com.bookstore.repository;

import com.bookstore.domain.book.PaymentForStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepositoryForStudent extends JpaRepository<PaymentForStudent, String> {
}
