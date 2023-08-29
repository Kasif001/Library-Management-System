package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Enums.Genre;
import com.example.librarymanagementsystem.RequestDtos.BookRequestDto;
import com.example.librarymanagementsystem.ResponseDto.AddBookResponseDto;
import com.example.librarymanagementsystem.ResponseDto.BookResponseDto;
import com.example.librarymanagementsystem.Services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j

public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public AddBookResponseDto addBook(@RequestBody BookRequestDto bookRequestDto){

        try {
            AddBookResponseDto responseDto =  bookService.addBook(bookRequestDto);
            return responseDto;
        }catch (Exception e){
            log.error("Author is not present",e.getMessage());
           return null;
        }
    }

    @GetMapping("/getBookByName")
    public ResponseEntity getBookByName(@RequestParam("name") String bookName){

        try {
            BookResponseDto responseDto = bookService.getBookByName(bookName);
            return new ResponseEntity(responseDto,HttpStatus.OK);
        }catch (Exception e){
            log.error("book by name not fount",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getBookByGenre")
    public ResponseEntity getBookByGenre(@RequestParam("genre")Genre genre){

        List<BookResponseDto> list = bookService.getAllBooksByGenre(genre);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
