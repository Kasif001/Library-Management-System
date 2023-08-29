package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor


@Entity
@Table

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;

    @Column(unique = true)
    private String title;

    private Integer pages;
    private boolean isAvailable;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private Date publicationDate;
    private Integer price;


    public Book(String title, Integer pages, boolean isAvailable, Genre genre, Date publicationDate, Integer price, Author author) {
        this.title = title;
        this.pages = pages;
        this.isAvailable = isAvailable;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.price = price;
        this.author = author;
    }

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Author author;


    @OneToMany(mappedBy = "book321",cascade = CascadeType.ALL)
    private List<Transactions> transactionsList = new ArrayList<>();

}
