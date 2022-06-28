package com.bookstore.service.impl;

import com.bookstore.domain.book.*;
import com.bookstore.domain.user.Employee;
import com.bookstore.domain.user.Student;
import com.bookstore.dto.request.GivebackRequest;
import com.bookstore.dto.request.BasketRequest;
import com.bookstore.dto.request.BookRequest;
import com.bookstore.dto.response.BookGenericResponse;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.BookExistException;
import com.bookstore.exception.UserNotFoundException;
import com.bookstore.repository.*;
import com.bookstore.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private static final String  debugId = "0c00f077-9373-42df-ad29-3de5b33b21ff";

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final BorrowRepositoryForStudent borrowRepositoryForStudent;
    private final BorrowRepositoryForEmployee borrowRepositoryForEmployee;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;
    private final PaymentRepositoryForStudent paymentRepositoryForStudent;
    private final PaymentRepositoryForEmployee paymentRepositoryForEmployee;
    private final AccountRepository accountRepository;

    @Override
    public BookGenericResponse saveBook(BookRequest bookRequest) {
        var isExistUser = isExistBook(bookRequest.getIsbn());
        if(!isExistUser){
            var savingModel = modelMapper.map(bookRequest, Book.class);
            return modelMapper.map(bookRepository.save(savingModel), BookGenericResponse.class);
        }
        else throw new BookExistException("The book already exist in database", bookRequest.getIsbn(), debugId);
    }

    @Override
    public BookGenericResponse updateBook(BookRequest book) {
        var isbn = book.getIsbn();
        var updatableBook = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Can't find in database this book -> ",isbn, debugId));
        extracted(book, updatableBook);
        return modelMapper.map(updatableBook,BookGenericResponse.class);
    }

    @Override
    public BookGenericResponse findBookByIdentity(String identity) {
        var isExistBook = isExistBook(identity);
        if(isExistBook){
            return modelMapper.map(bookRepository.findById(identity), BookGenericResponse.class);
        }
        else throw new BookNotFoundException("Can't find in database this book -> ",identity, debugId);
    }

    @Override
    public List<BookGenericResponse> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookGenericResponse.class))
                .toList();
    }

    @Override
    public void deleteBookById(String identity) {
        bookRepository.deleteById(identity);
    }

    @Override
    public List<Boolean> borrowForStudent(BasketRequest bookRequest) {
        var baskets = bookRequest.getItems();
        return baskets.stream()
                .map(
                        borrowRequest -> {
                            var book =  bookRepository.findById(borrowRequest.getBookIsbn())
                                    .orElseThrow(() -> new BookNotFoundException
                                            ("Can't find in database this book -> ",borrowRequest.getBookIsbn(), debugId));
                            var student = studentRepository.findById(borrowRequest.getUserId())
                                    .orElseThrow(() -> new UserNotFoundException
                                            ("Can't find in database this book -> ",borrowRequest.getBookIsbn(), debugId));
                            var isExistBorrowBefore =
                                    borrowRepositoryForStudent.findByBookIsbnAndAndUserId(book,student);

                            var borrow = new BorrowBookForStudent();

                            if(student.getStudentStatus().name().equals("STUDENT")){
                                if(!book.getBookType().toString().equals("READINGBOOK") || student.getHasBook()>=3)
                                    return Boolean.FALSE;
                                if(isExistBorrowBefore!=null){
                                    isExistBorrowBefore.setRightToExtend(isExistBorrowBefore.getRightToExtend() - 1 );
                                    borrowRepositoryForStudent.save(isExistBorrowBefore);
                                }
                                else{
                                    extracted(book, student);
                                }
                            }
                            else if(student.getStudentStatus().name().equals("GRADUATED")) {
                                if(!book.getBookType().toString().equals("READINGBOOK") || student.getHasBook()>=1)
                                    return Boolean.FALSE;
                                extracted(book, student);
                            }
                            return Boolean.TRUE;
                        }
                )
                .toList();


    }

    @Override
    public List<Boolean> borrowForEmployee(BasketRequest bookRequest) {
        var baskets = bookRequest.getItems();
        return baskets.stream()
                .map(
                        borrowRequest -> {
                            var book =  bookRepository.findById(borrowRequest.getBookIsbn())
                                    .orElseThrow(() -> new BookNotFoundException
                                            ("Can't find in database this book -> ",borrowRequest.getBookIsbn(), debugId));
                            var employee = employeeRepository.findById(borrowRequest.getUserId())
                                    .orElseThrow(() -> new UserNotFoundException
                                            ("Can't find in database this book -> ",borrowRequest.getBookIsbn(), debugId));
                            var isExistBorrowBefore =
                                    borrowRepositoryForEmployee.findByBookIsbnAndAndUserId(book,employee);

                            //var borrow = new BorrowBookForEmployee();

                            if(employee.getEmployeeType().name().equals("OFFICER")){
                                if(!book.getBookType().toString().equals("READINGBOOK") || employee.getHasBook()>=3)
                                    return Boolean.FALSE;
                                if(isExistBorrowBefore!=null){
                                    isExistBorrowBefore.setRightToExtend(isExistBorrowBefore.getRightToExtend() - 1 );
                                    borrowRepositoryForEmployee.save(isExistBorrowBefore);
                                }
                                else{
                                    extracted(book, employee,15);
                                }
                            }else if(employee.getEmployeeType().name().equals("INSTRUCTOR")) {
                                if(!book.getBookType().toString().equals("READINGBOOK") ||
                                        !book.getBookType().toString().equals("CLASSBOOK")|| employee.getHasBook()>=5)
                                    return Boolean.FALSE;
                                extracted(book, employee,30);
                            }
                            return Boolean.TRUE;
                        }
                )
                .toList();
    }

    private void extracted(Book book, Employee employee, int plusDeliveryDays) {
        var savedBorrow =
                BorrowBookForEmployee.builder()
                        .bookIsbn(book)
                        .userId(employee)
                        .rightToExtend(2)
                        .status(BorrowStatus.ACTIVE)
                        .deliveryDate(LocalDateTime.now().plusDays(plusDeliveryDays)).build();

        book.setStatus(BookStatus.PASSIVE);

        employee.setHasBook(employee.getHasBook() + 1 );

        bookRepository.save(book);
        employeeRepository.save(employee);
        borrowRepositoryForEmployee.save(savedBorrow);
    }

    private void extracted(Book book, Student student) {
        var savedBorrow =
                BorrowBookForStudent.builder()
                        .bookIsbn(book)
                        .userId(student)
                        .rightToExtend(2)
                        .status(BorrowStatus.ACTIVE)
                        .deliveryDate(LocalDateTime.now().plusDays(15)).build();

        book.setStatus(BookStatus.PASSIVE);

        student.setHasBook(student.getHasBook() + 1 );
        bookRepository.save(book);
        studentRepository.save(student);
        borrowRepositoryForStudent.save(savedBorrow);
    }

    private void extracted(BookRequest book, Book updatableBook) {
        updatableBook.setBookName(book.getBookName());
        updatableBook.setAuthor(book.getAuthor());
        updatableBook.setStatus(book.getStatus());
        updatableBook.setCover(Base64.decodeBase64(book.getCover()));
        updatableBook.setBookType(book.getBookType());
        bookRepository.save(updatableBook);
    }

    public boolean isExistBook(String isbn) {
        var user =  bookRepository.findById(isbn);
        return user.isPresent();
    }

    @Override
    public Boolean givebackToBookForStudent(GivebackRequest giveBackRequest){
        var giveBack = modelMapper.map(giveBackRequest, BorrowBookForStudent.class);

        if(giveBack.getStatus().equals(BorrowStatus.PASSIVE)){
            System.out.println("book is already given");
            return Boolean.FALSE;
        }
        else{
            var updatedBook = giveBack.getBookIsbn();
            updatedBook.setStatus(BookStatus.valueOf("ACTIVE"));
            updatedBook.setLastModifiedDate(LocalDateTime.now());

            var currentHasBook = giveBack.getUserId().getHasBook();
            var updatedStudent = giveBack.getUserId();
            updatedStudent.setHasBook(currentHasBook - 1);
            updatedStudent.setLastModifiedDate(LocalDateTime.now());

            giveBack.setStatus(BorrowStatus.valueOf("PASSIVE"));

            System.err.println("burada");
            bookRepository.save(updatedBook);
            studentRepository.save(updatedStudent);
            borrowRepositoryForStudent.save(giveBack);
            return Boolean.TRUE;
        }

    }
    @Override
    public Boolean givebackToBookForEmployee(GivebackRequest giveBackRequest){
        var giveBack = modelMapper.map(giveBackRequest, BorrowBookForEmployee.class);

        if(giveBack.getStatus().equals(BorrowStatus.PASSIVE)){
            System.out.println("book is already given");
            return Boolean.FALSE;
        }
        else{
            var updatedBook = giveBack.getBookIsbn();
            updatedBook.setStatus(BookStatus.valueOf("ACTIVE"));
            updatedBook.setLastModifiedDate(LocalDateTime.now());

            var currentHasBook = giveBack.getUserId().getHasBook();
            var updatedEmployee = giveBack.getUserId();
            updatedEmployee.setHasBook(currentHasBook -1);
            updatedEmployee.setLastModifiedDate(LocalDateTime.now());

            giveBack.setStatus(BorrowStatus.valueOf("PASSIVE"));

            bookRepository.save(updatedBook);
            employeeRepository.save(updatedEmployee);
            borrowRepositoryForEmployee.save(giveBack);
            return Boolean.TRUE;
        }
    }



    //arkaplanda oto yapılması lazım
    //eksik olarak -> account 0'ın altına düşerse kontrol yok..
    //             -> deliveryDay'e belirli gün kala sms atılmalı
    void punishmentControlForStudent(BorrowBookForStudent borrow){
        LocalDate currentDate = LocalDate.now();
        LocalDate deliveryDate = LocalDate.from(borrow.getDeliveryDate());
        //if(currentDate.toEpochDay() - deliveryDate.toEpochDay() >=7){
        if(borrow.getStatus().equals("ACTIVE") && currentDate.datesUntil(deliveryDate).count() >= 7){
            var amount = 10;
            var newBalance = borrow.getUserId().getAccount().getBalance();
            newBalance -= amount;
            var newPunishDate = currentDate;

            while(!borrow.getBookIsbn().getStatus().equals("ACTIVE")){  //kitap teslim edilmediği sürece
                if(LocalDate.now().datesUntil(newPunishDate).count() >=7){
                    amount += 20;
                    newBalance -= 20;
                    newPunishDate = LocalDate.now();
                }
            }
            //tablolara kaydedilecek
            //1-payment'ın amountu ve diğerleri
            var payment = PaymentForStudent.builder()
                    .amount(amount)
                    .paymentType(PaymentType.valueOf("PUNISH"))
                    .studentId(borrow.getUserId()).build();
            paymentRepositoryForStudent.save(payment);
            //2-account'unun balance'ı
            var student = borrow.getUserId();
            var account = student.getAccount();
            account.setBalance(newBalance);
            accountRepository.save(account);
        }
    }
    void punishmentControlForEmployee(BorrowBookForEmployee borrow){
        LocalDate currentDate = LocalDate.now();
        LocalDate deliveryDate = LocalDate.from(borrow.getDeliveryDate());
        //if(currentDate.toEpochDay() - deliveryDate.toEpochDay() >=7){
        if(borrow.getStatus().equals("ACTIVE") && currentDate.datesUntil(deliveryDate).count() >= 7){
            var amount = 10;
            var newBalance = borrow.getUserId().getAccount().getBalance();
            newBalance -= amount;
            var newPunishDate = currentDate;

            while(!borrow.getBookIsbn().getStatus().equals("ACTIVE")){  //kitap teslim edilmediği sürece
                if(LocalDate.now().datesUntil(newPunishDate).count() >=7){
                    amount += 20;
                    newBalance -= 20;
                    newPunishDate = LocalDate.now();
                }
            }
            //tablolara kaydedilecek
            //1-payment'ın amountu ve diğerleri
            var payment = PaymentForEmployee.builder()
                    .amount(amount)
                    .paymentType(PaymentType.valueOf("PUNISH"))
                    .employeeId(borrow.getUserId()).build();
            paymentRepositoryForEmployee.save(payment);
            //2-student'ın account'unun balance'ı
            var employee = borrow.getUserId();
            var account = employee.getAccount();
            account.setBalance(newBalance);
            accountRepository.save(account);
        }

    }

}
