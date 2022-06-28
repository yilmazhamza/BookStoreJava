package com.bookstore.dto.response;

import com.bookstore.domain.user.Account;
import com.bookstore.domain.user.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String identityNo;
    private String fullName;
    private String mail;
    private String password;
    private String phone;
    private int hasBook;
    private Account account;
    private String studentNo;
    private StudentStatus studentStatus;
}
