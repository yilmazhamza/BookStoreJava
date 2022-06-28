package com.bookstore.repository;

import com.bookstore.domain.book.ReservationForStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepositoryForStudent extends JpaRepository<ReservationForStudent, Long> {


}
