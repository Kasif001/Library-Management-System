package com.example.librarymanagementsystem.RequestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateNameAndPenNameRequestDto {
    private Integer id;
    private String newName;
    private String newPenName;
}
