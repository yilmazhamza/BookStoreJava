package com.bookstore.dto.request;

import com.bookstore.domain.user.Account;
import com.bookstore.domain.user.EmployeeType;
import com.bookstore.validation.StrongPassword;
import com.bookstore.validation.TcKimlikNo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EmployeeRequest {

    @TcKimlikNo
    private String identityNo;

    @NotBlank
    @Length(min = 6, max = 50)
    private String fullName;

    @Email
    private String mail;

    @NotBlank
    @StrongPassword
    private String password;

    @NotBlank
    private String phone;

    @NotNull
    private Account account;

    @NotNull
    private EmployeeType employeeType;

}
