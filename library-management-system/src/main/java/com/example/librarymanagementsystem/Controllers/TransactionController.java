package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.ResponseDto.GetTransactionDetailsRespDto;
import com.example.librarymanagementsystem.Services.TransactionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@Slf4j


public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/issueBook")
    public ResponseEntity issue(@RequestParam("bookId")Integer bookId, @RequestParam("libraryCardId")Integer id){

        try{
            String result = transactionService.issueBook(bookId,id);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("Something problem with bookId or cardId",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }

    }

    @PutMapping("/returnBook")
    public ResponseEntity returnBook(@RequestParam("cardId")Integer cardId, @RequestParam("bookId")Integer bookId){

        try{
            String result = transactionService.returnBook(cardId,bookId);
            return new ResponseEntity(result,HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("Something error in bookId or cardId",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getAllTransactionDetailsByLibraryCard")
    public ResponseEntity getDetails(@RequestParam("cardNo") Integer cardNo){
        try{
            List<GetTransactionDetailsRespDto> list = transactionService.findTransciontDetails(cardNo);
            return new ResponseEntity(list,HttpStatus.ACCEPTED);
        }catch (Exception e){
            log.error("Something error in bookId or cardId",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
