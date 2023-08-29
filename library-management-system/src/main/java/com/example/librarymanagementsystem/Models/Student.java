package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enums.CardStatus;
import com.example.librarymanagementsystem.Enums.Department;
import com.example.librarymanagementsystem.Enums.Gender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rollNo;

    private String name;


    @Enumerated(value = EnumType.STRING)
    private Gender gender; //This is our costumize user define datatype contains 2 values

    @Enumerated(value = EnumType.STRING)//This will trate this variable as String datatype
    private CardStatus cardStatus;

    @Enumerated(value = EnumType.STRING)
    private Department department;

    @Column(unique = true)
    private String email;

    @OneToOne(mappedBy = "student123123",cascade = CascadeType.ALL)
    LibraryCard libraryCard;

}
