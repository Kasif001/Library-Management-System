package com.example.librarymanagementsystem.ResponseDto;

import com.example.librarymanagementsystem.Enums.Department;
import com.example.librarymanagementsystem.Enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetStudentByCardRespDto {
    private String name;
    private Gender gender;
    private Department department;

}
