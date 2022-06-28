package com.bookstore.domain.user;

import com.bookstore.validation.StrongPassword;
import com.bookstore.validation.TcKimlikNo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee{

    @Id
    @TcKimlikNo
    @Column(nullable = false,unique = true,name = "user_id")
    private String identityNo;

    @CreatedDate
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @NotBlank
    @Length(min = 6, max = 50)
    private String fullName;

    @Column(nullable = false, unique = true)
    @Email
    private String mail;

    @NotBlank
    @StrongPassword
    @JsonIgnore
    private String password;


    @NotBlank
    @Column(unique = true,nullable = false)
    private String phone;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Column(nullable = false)
    private int hasBook = 0;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return identityNo.equals(employee.identityNo);
    }

    @Override
    public int hashCode() {
        return identityNo.hashCode();
    }


}
