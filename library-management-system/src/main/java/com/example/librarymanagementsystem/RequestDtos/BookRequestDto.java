package com.example.librarymanagementsystem.RequestDtos;

import com.example.librarymanagementsystem.Enums.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookRequestDto {
    private int authorId;
    private String title;
    private Integer pages;
    private boolean isAvailable;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private Date publicationDate;
    private Integer price;
}
