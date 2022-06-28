package com.bookstore.domain.user;

import com.bookstore.validation.Iban;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Iban
    private String cardNo;

    @Column(nullable = false)
    private double balance;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;

        return id == account.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
