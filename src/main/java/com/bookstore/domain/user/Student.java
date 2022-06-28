package com.bookstore.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
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
@Table(name = "students")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Student{

    @Id
    @NotBlank
    @Column(nullable = false,unique = true,name = "user_id")
    private String identityNo;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    @NotBlank
    @Column(nullable = false)
    @Length(min = 6, max = 50)
    private String fullName;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String mail;

    @NotBlank
    @Column(nullable = false)
    @JsonIgnore
    private String password;


    @NotBlank
    @Column(unique = true,nullable = false)
    private String phone;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    @Column(nullable = false,unique = true)
    @Length(min = 9, max = 9)
    @NotBlank
    private String studentNo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StudentStatus studentStatus;

    @Column(nullable = false)
    private int hasBook = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;
        return studentNo.equals(student.studentNo);
    }
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + studentNo.hashCode();
        return result;
    }
}
