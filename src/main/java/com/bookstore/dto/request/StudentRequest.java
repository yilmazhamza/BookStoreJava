package com.bookstore.dto.request;


import com.bookstore.domain.user.Account;
import com.bookstore.domain.user.StudentStatus;
import com.bookstore.validation.TcKimlikNo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @TcKimlikNo
    private String identityNo;

    @NotBlank
    @Length(min = 6, max = 50)
    private String fullName;

    @Email
    @NotBlank
    private String mail;

    @NotBlank
    private String password;

    @Transient
    private int hasBook;

    @NotBlank
    private String phone;

    @NotNull
    private Account account;

    @Length(min = 9, max = 9)
    @NotBlank
    private String studentNo;

    @NotNull
    private StudentStatus studentStatus;

}
