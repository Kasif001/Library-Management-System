package com.example.librarymanagementsystem.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Getter
@Setter

public class AddBookResponseDto {

    private String title;
    private String authorName;

    public AddBookResponseDto(String title, String authorName) {
        this.title = title;
        this.authorName = authorName;
    }
}
