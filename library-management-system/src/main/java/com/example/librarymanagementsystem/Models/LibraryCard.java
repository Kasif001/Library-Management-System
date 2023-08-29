package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enums.CardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardNo;

    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus;

    private int noOfBooksIssued;

    private String booksList; //Harry Potter, Mahesh ka bel

    //Forgein key is add to this child table
    @OneToOne
    @JoinColumn
    private Student student123123;

    @OneToMany(mappedBy = "libraryCard321",cascade = CascadeType.ALL)
    private List<Transactions> transactionsList = new ArrayList<>();

}
