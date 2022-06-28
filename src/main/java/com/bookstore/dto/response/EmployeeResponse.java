package com.bookstore.dto.response;


import com.bookstore.domain.user.Account;
import com.bookstore.domain.user.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private String identityNo;
    private String fullName;
    private String mail;
    private String password;
    private String phone;
    private int hasBook;
    private Account account;
    private EmployeeType employeeType;
}
