package com.bookstore.domain.book;

import com.bookstore.domain.user.Student;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "payment")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PaymentForStudent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @OneToOne(fetch = FetchType.LAZY,targetEntity= PaymentForStudent.class)
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private Student studentId;

    @Enumerated
    @NotNull
    private PaymentType paymentType;

    @NotNull
    @Positive
    private double amount;

}
