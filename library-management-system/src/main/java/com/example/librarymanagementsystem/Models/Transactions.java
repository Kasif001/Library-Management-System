package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enums.TransactionStatus;
import com.example.librarymanagementsystem.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor



@Entity
@Table(name = "Transaction")

public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    private Integer fineAmmount;

    public Transactions(TransactionType transactionType, TransactionStatus transactionStatus, Integer fineAmmount) {
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.fineAmmount = fineAmmount;
    }

    @ManyToOne
    @JoinColumn
    private LibraryCard libraryCard321;


    @ManyToOne
    @JoinColumn
    private Book book321;

}
