package com.bookstore.domain.book;

public enum ReservationStatus {
    ACTIVE, //reservasyon oluşturma
    BORROWED, //reservasyon işleminin borrow_book tablosuna geçmesi
    CANCELED //reservasyon iptal
}
