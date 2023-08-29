package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.RequestDtos.AddAuthorReqDto;
import com.example.librarymanagementsystem.RequestDtos.UpdateNameAndPenNameRequestDto;
import com.example.librarymanagementsystem.Services.AuthorService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
@Slf4j

public class AuthorController {
    @Autowired
    private AuthorService authorService;


    @PostMapping("/addAuthor")
    public ResponseEntity add(@RequestBody AddAuthorReqDto addAuthorReqDto){

        try{
            String result = authorService.addAuthor(addAuthorReqDto);
            return new ResponseEntity(result,HttpStatus.OK);
        }catch (Exception e){
            log.error("Do not send author id",e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getAuthorObj")
    public Author get(@RequestParam("id")Integer id){
        try{
            return authorService.getAuthor(id);
        }catch (Exception e){
            log.error("Id is invalid",e.getMessage());
            return null;
        }
    }


    @PutMapping("/updateNameAndPenName")
    public ResponseEntity updatation(@RequestBody UpdateNameAndPenNameRequestDto updateNameAndPenNameRequestDto){

        try{
            String result = authorService.updation(updateNameAndPenNameRequestDto);
            return new ResponseEntity(result,HttpStatus.OK);
        }catch (Exception e){
            log.error("Author not exists",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}
