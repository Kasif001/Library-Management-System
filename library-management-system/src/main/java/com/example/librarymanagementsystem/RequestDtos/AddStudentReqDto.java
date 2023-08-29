package com.example.librarymanagementsystem.RequestDtos;

import com.example.librarymanagementsystem.Enums.CardStatus;
import com.example.librarymanagementsystem.Enums.Department;
import com.example.librarymanagementsystem.Enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddStudentReqDto {
    private String name;
    private Gender  gender; //This is our costumize user define datatype contains 2 values
    private CardStatus cardStatus;
    private Department department;
    private String email;
}
