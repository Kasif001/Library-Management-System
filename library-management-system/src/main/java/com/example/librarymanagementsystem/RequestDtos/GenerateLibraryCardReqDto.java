package com.example.librarymanagementsystem.RequestDtos;

import com.example.librarymanagementsystem.Enums.CardStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GenerateLibraryCardReqDto {
    private CardStatus cardStatus;
    private int noOfBooksIssued;
}
