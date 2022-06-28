package com.bookstore.domain.book;

import com.bookstore.domain.user.Employee;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_book_employee")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BorrowBookForEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false,name = "book_isbn")
    @ToString.Exclude
    private Book bookIsbn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private Employee userId;

    @NotNull
    @CreatedDate
    private LocalDateTime processDate;

    private LocalDateTime deliveryDate;

    @Max(3)
    @Min(0)
    @NotNull
    private int rightToExtend;

    @NotNull
    private BorrowStatus status = BorrowStatus.ACTIVE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorrowBookForEmployee that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

