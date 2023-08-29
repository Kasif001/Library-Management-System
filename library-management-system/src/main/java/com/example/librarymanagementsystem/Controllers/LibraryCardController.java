package com.example.librarymanagementsystem.Controllers;


import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.RequestDtos.GenerateLibraryCardReqDto;
import com.example.librarymanagementsystem.ResponseDto.GetStudentByCardRespDto;
import com.example.librarymanagementsystem.Services.LibraryCardService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/LibraryCard")
@Slf4j

public class LibraryCardController {

    @Autowired
    private LibraryCardService libraryCardService;

    @PostMapping("/addCard")
    public String addCard(@RequestBody GenerateLibraryCardReqDto generateLibraryCardReqDtolibraryCard){

        return libraryCardService.addCards(generateLibraryCardReqDtolibraryCard);
    }

    @GetMapping("/findStudentByCard")
    public ResponseEntity getStudentDetails(@RequestParam("cardId")Integer cardId){
        try{
            GetStudentByCardRespDto respDto = libraryCardService.getStudent(cardId);
            return new ResponseEntity(respDto,HttpStatus.OK);
        }catch (Exception e){
            log.error("find student by card id error",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/issueToStudent")
    public ResponseEntity issueToStudents(@RequestParam("cardId")Integer cardId, @RequestParam("rollNo")Integer rollNo){

        try{
            String result = libraryCardService.assignToStudent(cardId,rollNo);
            return new ResponseEntity(result, HttpStatus.OK);
        }catch (Exception e){
            log.error("Something error in Student or Library Card",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }


}
