package com.example.librarymanagementsystem.RequestDtos;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddAuthorReqDto {
    private String name;
    private String email;
    private Integer age;
    private String penName;

}
