package com.bookstore.config;

import com.bookstore.domain.book.*;
import com.bookstore.domain.user.Employee;
import com.bookstore.domain.user.Student;
import com.bookstore.dto.request.GivebackRequest;
import com.bookstore.dto.request.*;
import com.bookstore.dto.response.BookGenericResponse;
import com.bookstore.dto.response.EmployeeResponse;
import com.bookstore.dto.response.StudentResponse;
import com.bookstore.repository.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    private static BookRepository bookRepository;
    private static StudentRepository studentRepository;
    private static EmployeeRepository employeeRepository;
    private static BorrowRepositoryForStudent borrowRepositoryForStudent;
    private static BorrowRepositoryForEmployee borrowRepositoryForEmployee;

    public ConverterConfig(BookRepository bookRepository,
                           StudentRepository studentRepository,
                           EmployeeRepository employeeRepository,
                           BorrowRepositoryForStudent borrowRepositoryForStudent,
                           BorrowRepositoryForEmployee borrowRepositoryForEmployee) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.employeeRepository = employeeRepository;
        this.borrowRepositoryForStudent = borrowRepositoryForStudent;
        this.borrowRepositoryForEmployee = borrowRepositoryForEmployee;
    }

    private static final Converter<Book, BookGenericResponse> BOOK_TO_BOOK_RESPONSE_CONVERTER =
            context -> new BookGenericResponse(
                    context.getSource().getIsbn(),
                    context.getSource().getBookName(),
                    context.getSource().getAuthor(),
                    context.getSource().getCoverBase64(),
                    context.getSource().getPublishYear(),
                    context.getSource().getBookType(),
                    context.getSource().getStatus(),
                    context.getSource().getPublisher());

    private static final Converter<BookRequest, Book> BOOK_REQUEST_TO_BOOK_CONVERTER =
            context -> Book.builder()
                            .isbn( context.getSource().getIsbn())
                            .bookName( context.getSource().getBookName())
                            .author( context.getSource().getAuthor())
                            .cover(context.getSource().getBase64Cover())
                            .publishYear(context.getSource().getPublishYear())
                            .bookType(context.getSource().getBookType())
                            .status( context.getSource().getStatus())
                            .publisher( context.getSource().getPublisher())
                            .build();

    private static final Converter<Employee, EmployeeResponse> EMPLOYEE_TO_EMPLOYEE_RESPONSE_CONVERTER =
            context -> new EmployeeResponse(
                    context.getSource().getIdentityNo(),
                    context.getSource().getFullName(),
                    context.getSource().getMail(),
                    context.getSource().getPassword(),
                    context.getSource().getPhone(),
                    context.getSource().getHasBook(),
                    context.getSource().getAccount(),
                    context.getSource().getEmployeeType());

    private static final Converter<EmployeeRequest, Employee> EMPLOYEE_REQUEST_TO_EMPLOYEE_CONVERTER =
            context -> Employee.builder()
                    .identityNo( context.getSource().getIdentityNo())
                    .fullName( context.getSource().getFullName())
                    .mail( context.getSource().getMail())
                    .password(context.getSource().getPassword())
                    .phone(context.getSource().getPhone())
                    .account(context.getSource().getAccount())
                    .employeeType( context.getSource().getEmployeeType())
                    .build();

    private static final Converter<Student, StudentResponse> STUDENT_TO_STUDENT_RESPONSE_CONVERTER =
            context -> new StudentResponse(
                    context.getSource().getIdentityNo(),
                    context.getSource().getFullName(),
                    context.getSource().getMail(),
                    context.getSource().getPassword(),
                    context.getSource().getPhone(),
                    context.getSource().getHasBook(),
                    context.getSource().getAccount(),
                    context.getSource().getStudentNo(),
                    context.getSource().getStudentStatus());

    private static final Converter<StudentRequest, Student> STUDENT_REQUEST_TO_STUDENT_CONVERTER =
            context -> Student.builder()
                    .identityNo( context.getSource().getIdentityNo())
                    .fullName( context.getSource().getFullName())
                    .mail( context.getSource().getMail())
                    .password(context.getSource().getPassword())
                    .phone(context.getSource().getPhone())
                    .account(context.getSource().getAccount())
                    .studentNo( context.getSource().getStudentNo())
                    .studentStatus( context.getSource().getStudentStatus())
                    .build();

    private static final Converter<BorrowRequest, BorrowBookForStudent> BORROW_REQUEST_TO_BORROW_BOOK_FOR_STUDENT_CONVERTER =

            context -> {
                        var book = bookRepository.findById(context.getSource().getBookIsbn());
                        var student = studentRepository.findById(context.getSource().getUserId());
                        return BorrowBookForStudent.builder()
                                    .bookIsbn(book.get())
                                    .userId(student.get())
                                    .build();

            };

    private static final Converter<BorrowRequest, BorrowBookForEmployee> BORROW_REQUEST_TO_BORROW_BOOK_FOR_EMPLOYEE_CONVERTER =
            context -> {
                        var book = bookRepository.findById(context.getSource().getBookIsbn());
                        var employee = employeeRepository.findById(context.getSource().getUserId());
                        return BorrowBookForEmployee.builder()
                                    .bookIsbn( book.get())
                                    .userId( employee.get())
                                    .build();
            };

    private static final Converter<GivebackRequest, BorrowBookForStudent> GIVEBACK_REQUEST_TO_BORROW_BOOK_FOR_STUDENT_CONVERTER =
            context -> {
                var book = bookRepository.findById(context.getSource().getBookIsbn());
                var student = studentRepository.findById(context.getSource().getUserId());
                var borrowBookForStudent = borrowRepositoryForStudent.findByBookIsbnAndAndUserId(book.get(),student.get());
                return borrowBookForStudent;
            };

    private static final Converter<GivebackRequest, BorrowBookForEmployee> GIVEBACK_REQUEST_TO_BORROW_BOOK_FOR_EMPLOYEE_CONVERTER =
            context -> {
                var book = bookRepository.findById(context.getSource().getBookIsbn());
                var employee = employeeRepository.findById(context.getSource().getUserId());
                var borrowBookForEmployee = borrowRepositoryForEmployee.findByBookIsbnAndAndUserId(book.get(),employee.get());
                return borrowBookForEmployee;
            };

    private static final Converter<ReservationRequest, ReservationForStudent> RESERVATION_REQUEST_TO_RESERVATION_FOR_STUDENT_CONVERTER =
            context -> {
                        var book = bookRepository.findById(context.getSource().getBookIsbn());
                        var student = studentRepository.findById(context.getSource().getUserId());
                        return  ReservationForStudent.builder()
                                .bookIsbn( book.get())
                                .userId( student.get())
                                .reservationStatus( ReservationStatus.ACTIVE)
                                .build();
            };

    private static final Converter<ReservationRequest, ReservationForEmployee> RESERVATION_REQUEST_TO_RESERVATION_FOR_EMPLOYEE_CONVERTER =
            context -> {
                var book = bookRepository.findById(context.getSource().getBookIsbn());
                var employee = employeeRepository.findById(context.getSource().getUserId());
                return  ReservationForEmployee.builder()
                        .bookIsbn( book.get())
                        .userId(employee.get())
                        .reservationStatus( ReservationStatus.ACTIVE)
                        .build();
            };

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        modelMapper.addConverter(BOOK_TO_BOOK_RESPONSE_CONVERTER, Book.class, BookGenericResponse.class);
        modelMapper.addConverter(BOOK_REQUEST_TO_BOOK_CONVERTER, BookRequest.class, Book.class);
        modelMapper.addConverter(EMPLOYEE_REQUEST_TO_EMPLOYEE_CONVERTER, EmployeeRequest.class, Employee.class);
        modelMapper.addConverter(EMPLOYEE_TO_EMPLOYEE_RESPONSE_CONVERTER, Employee.class, EmployeeResponse.class);
        modelMapper.addConverter(STUDENT_REQUEST_TO_STUDENT_CONVERTER, StudentRequest.class, Student.class);
        modelMapper.addConverter(STUDENT_TO_STUDENT_RESPONSE_CONVERTER, Student.class, StudentResponse.class);
        modelMapper.addConverter(BORROW_REQUEST_TO_BORROW_BOOK_FOR_STUDENT_CONVERTER, BorrowRequest.class, BorrowBookForStudent.class);
        modelMapper.addConverter(GIVEBACK_REQUEST_TO_BORROW_BOOK_FOR_STUDENT_CONVERTER, GivebackRequest.class, BorrowBookForStudent.class);
        modelMapper.addConverter(BORROW_REQUEST_TO_BORROW_BOOK_FOR_EMPLOYEE_CONVERTER, BorrowRequest.class, BorrowBookForEmployee.class);
        modelMapper.addConverter(GIVEBACK_REQUEST_TO_BORROW_BOOK_FOR_EMPLOYEE_CONVERTER, GivebackRequest.class, BorrowBookForEmployee.class);
        modelMapper.addConverter(RESERVATION_REQUEST_TO_RESERVATION_FOR_STUDENT_CONVERTER, ReservationRequest.class, ReservationForStudent.class);
        modelMapper.addConverter(RESERVATION_REQUEST_TO_RESERVATION_FOR_EMPLOYEE_CONVERTER, ReservationRequest.class, ReservationForEmployee.class);
        return modelMapper;
    }

}
