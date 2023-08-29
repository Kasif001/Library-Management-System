package com.example.librarymanagementsystem.Models;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer authorId;


    private String name;

    private String email;
    private Integer age;

    @Column(unique = true) // pen name should be unique
    private String penName;


    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Book> bookList = new ArrayList<>();
}
