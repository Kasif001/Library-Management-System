package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.RequestDtos.AddStudentReqDto;
import com.example.librarymanagementsystem.ResponseDto.getLibraryCardRespDto;
import com.example.librarymanagementsystem.Services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Slf4j

public class StudentController {

    @Autowired
    private StudentService studentService;
    @PostMapping("/addStudent")
    public ResponseEntity add(@RequestBody AddStudentReqDto addStudentReqDto){
        try{
            String res =  studentService.addStudent(addStudentReqDto);
            return new ResponseEntity(res, HttpStatus.CREATED);
        }catch (Exception e){
            log.error("Student not add Successfully",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/findLibraryCardByRollNo")
    public ResponseEntity findCard(@RequestParam("rollNo") Integer rollNo){
        try{
            getLibraryCardRespDto respDto = studentService.findCard(rollNo);
            return new ResponseEntity(respDto,HttpStatus.OK);
        }catch (Exception e){
            log.error("find student's library card by id is stuck due to rollNo or library card Object",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/findDepartment")
    public ResponseEntity findById(@RequestParam("id")Integer id){
        try {
            String res = studentService.findDepartment(id);
            return new ResponseEntity(res,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);

        }
    }
}
