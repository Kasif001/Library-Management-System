package com.example.librarymanagementsystem.ResponseDto;

import com.example.librarymanagementsystem.Enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

//ALL Available books List Or Response

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class BookResponseDto {
    private String title;
    private String authorName;
    private Genre genre;
    private Integer pages;
    private Date publicationDate;
    private Integer price;

}
