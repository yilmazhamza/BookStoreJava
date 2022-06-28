package com.bookstore.repository;

import com.bookstore.domain.book.ReservationForEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepositoryForEmployee extends JpaRepository<ReservationForEmployee, Long> {

}
