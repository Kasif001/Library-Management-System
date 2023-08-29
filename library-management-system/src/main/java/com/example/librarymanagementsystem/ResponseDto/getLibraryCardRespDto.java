package com.example.librarymanagementsystem.ResponseDto;

import com.example.librarymanagementsystem.Enums.CardStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class getLibraryCardRespDto{
    private int cardNo;
    private CardStatus cardStatus;
    private int noOfBooksIssued;
}
