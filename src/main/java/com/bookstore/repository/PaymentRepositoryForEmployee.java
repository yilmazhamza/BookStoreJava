package com.bookstore.repository;

import com.bookstore.domain.book.PaymentForEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepositoryForEmployee extends JpaRepository<PaymentForEmployee, String> {
}
