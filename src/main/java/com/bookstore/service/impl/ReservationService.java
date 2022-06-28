package com.bookstore.service.impl;

import com.bookstore.domain.book.*;
import com.bookstore.domain.user.EmployeeType;
import com.bookstore.domain.user.StudentStatus;
import com.bookstore.dto.request.*;
import com.bookstore.repository.ReservationRepositoryForEmployee;
import com.bookstore.repository.ReservationRepositoryForStudent;
import com.bookstore.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@RequiredArgsConstructor
@Service
public class ReservationService implements IReservationService {

    private final ReservationRepositoryForStudent reservationRepositoryForStudent;
    private final ReservationRepositoryForEmployee reservationRepositoryForEmployee;
    private final BookService bookService;
    private final StudentService studentService;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Override
    public Boolean reservationForStudent(ReservationRequest reservationRequest) {

        var reservationForStudent = modelMapper.map(reservationRequest, ReservationForStudent.class);
        var checkedBook = bookService.findBookByIdentity(reservationForStudent.getBookIsbn().getIsbn());
        var checkedStudent = studentService.findStudentByIdentityNo(reservationForStudent.getUserId().getIdentityNo());

        if (checkedBook.getStatus().equals(BookStatus.ACTIVE) &&
                checkedStudent.getStudentStatus().equals(StudentStatus.STUDENT) &&
                checkedStudent.getHasBook()<=2){

            //sınırsız reservasyon yapamasın diye reservasyon yaptıkça sahip olunan kitap sayısını 1 arttırdım.
            checkedStudent.setHasBook(checkedStudent.getHasBook()+1);
            studentService.updateStudent(modelMapper.map(checkedStudent, StudentRequest.class));

            checkedBook.setStatus(BookStatus.RESERVATION);
            bookService.updateBook(modelMapper.map(checkedBook,BookRequest.class));

            reservationForStudent.setReservationStatus(ReservationStatus.ACTIVE);
            reservationRepositoryForStudent.save(reservationForStudent);
            return true;


        }else if(checkedBook.getStatus().equals(BookStatus.ACTIVE) &&
                checkedStudent.getStudentStatus().equals(StudentStatus.GRADUATED) ){

            studentService.updateStudent(modelMapper.map(checkedStudent, StudentRequest.class));
            //doldurulacak
            return true;
        }else
            return false;
    }

    @Override
    public Boolean reservationForEmployee(ReservationRequest reservationRequest) {

        var reservationForEmployee = modelMapper.map(reservationRequest, ReservationForEmployee.class);
        var checkedBook = bookService.findBookByIdentity(reservationForEmployee.getBookIsbn().getIsbn());
        var checkedEmployee = employeeService.findEmployeeByIdentityNo(reservationForEmployee.getUserId().getIdentityNo());

        if (checkedBook.getStatus().equals(BookStatus.ACTIVE) &&
                checkedEmployee.getEmployeeType().equals(EmployeeType.INSTRUCTOR) &&
                checkedEmployee.getHasBook()<=5){

            //sınırsız reservasyon yapamasın diye reservasyon yaptıkça sahip olunan kitap sayısını 1 arttırdım.
            checkedEmployee.setHasBook(checkedEmployee.getHasBook()+1);
            employeeService.updateEmployee(modelMapper.map(checkedEmployee, EmployeeRequest.class));

            checkedBook.setStatus(BookStatus.RESERVATION);
            bookService.updateBook(modelMapper.map(checkedBook,BookRequest.class));

            reservationForEmployee.setReservationStatus(ReservationStatus.ACTIVE);
            reservationRepositoryForEmployee.save(reservationForEmployee);
            return true;


        }else if(checkedBook.getStatus().equals(BookStatus.ACTIVE) &&
                checkedEmployee.getEmployeeType().equals(EmployeeType.OFFICER)){

            employeeService.updateEmployee(modelMapper.map(checkedEmployee, EmployeeRequest.class));
            //doldurulacak
            return true;
        }else
            return false;
    }



    // admin reservasyon onaylama/iptalEtme kısmı
    public Boolean adminReservationControlForStudent(ReservationForStudent reservation){

        if(reservation.getReservationStatus().equals(ReservationStatus.BORROWED)) {
            var book = reservation.getBookIsbn();
            book.setStatus(BookStatus.PASSIVE);
            var bookReq = modelMapper.map(book, BookRequest.class);

            var borrowReq = new BorrowRequest();
            borrowReq.setBookIsbn(reservation.getBookIsbn().getIsbn());
            borrowReq.setUserId(reservation.getUserId().getIdentityNo());

            Collection<BorrowRequest> collection = new ArrayList<>();
            collection.add(borrowReq);
            var basketReq = new BasketRequest(collection);

            bookService.updateBook(bookReq);
            reservationRepositoryForStudent.save(reservation);
            bookService.borrowForStudent(basketReq);
            return true;
        }
        else if(reservation.getReservationStatus().equals(ReservationStatus.CANCELED)){
            var book = reservation.getBookIsbn();
            book.setStatus(BookStatus.ACTIVE);
            var bookReq = modelMapper.map(book, BookRequest.class);

            bookService.updateBook(bookReq);
            reservationRepositoryForStudent.save(reservation);
            return true;
        }else { //active olan reservasyonu yine active olarak değiştirmeye çalışırsa ya da alakasız işlem yapmaya çalışırsa false dön
            return false;
        }
    }

    public Boolean adminReservationControlForEmployee(ReservationForEmployee reservation){

        if(reservation.getReservationStatus().equals(ReservationStatus.BORROWED)) {
            var book = reservation.getBookIsbn();
            book.setStatus(BookStatus.PASSIVE);
            var bookReq = modelMapper.map(book, BookRequest.class);

            var borrowReq = new BorrowRequest();
            borrowReq.setBookIsbn(reservation.getBookIsbn().getIsbn());
            borrowReq.setUserId(reservation.getUserId().getIdentityNo());

            Collection<BorrowRequest> collection = new ArrayList<>();
            collection.add(borrowReq);
            var basketReq = new BasketRequest(collection);

            bookService.updateBook(bookReq);
            reservationRepositoryForEmployee.save(reservation);
            bookService.borrowForEmployee(basketReq);
            return true;
        }
        else if(reservation.getReservationStatus().equals(ReservationStatus.CANCELED)){
            var book = reservation.getBookIsbn();
            book.setStatus(BookStatus.ACTIVE);
            var bookReq = modelMapper.map(book, BookRequest.class);

            bookService.updateBook(bookReq);
            reservationRepositoryForEmployee.save(reservation);
            return true;
        }else { //active olan reservasyonu yine active olarak değiştirmeye çalışırsa ya da alakasız işlem yapmaya çalışırsa false dön
            return false;
        }
    }



}
