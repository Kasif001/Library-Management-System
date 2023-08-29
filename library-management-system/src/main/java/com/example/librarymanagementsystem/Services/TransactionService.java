package com.example.librarymanagementsystem.Services;


import com.example.librarymanagementsystem.CustomExceptions.BookNotFoundException;
import com.example.librarymanagementsystem.CustomExceptions.LibraryCardNotFoundException;
import com.example.librarymanagementsystem.Enums.TransactionStatus;
import com.example.librarymanagementsystem.Enums.TransactionType;
import com.example.librarymanagementsystem.Models.Book;
import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Models.Transactions;
import com.example.librarymanagementsystem.Repositries.BookRepository;
import com.example.librarymanagementsystem.Repositries.LibraryCardRepository;
import com.example.librarymanagementsystem.Repositries.TransactionRepository;
import com.example.librarymanagementsystem.ResponseDto.GetTransactionDetailsRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.example.librarymanagementsystem.Enums.CardStatus.*;

@Service
public class TransactionService {


//  Using global variable mentioned in application property class
    public static final Integer bookMaxLimit = 6;
    public static final Integer perDayFineAmmount = 5;



    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryCardRepository libraryCardRepository;


    public String issueBook(Integer bookId, Integer cardId) throws Exception{

//      We have to record failed transactions also
        Transactions transactions = new Transactions(TransactionType.ISSUE,TransactionStatus.PENDING,0);


//      Validations

//      Book related Exceptional Handling
        if(!bookRepository.existsById(bookId)){
            throw new BookNotFoundException("Book not found");
        }
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.get();
        if(!book.isAvailable()){
            throw new BookNotFoundException("This book not available ");
        }


//      Card related Exceptional Handling
        if(!libraryCardRepository.existsById(cardId)){
            throw new LibraryCardNotFoundException("Card not exists");
        }
        Optional<LibraryCard> optionalLibraryCard = libraryCardRepository.findById(cardId);
        LibraryCard libraryCard = optionalLibraryCard.get();

        if(libraryCard.getCardStatus() == DEACTIVE){
            transactions.setTransactionStatus(TransactionStatus.FAILED);
            transactions = transactionRepository.save(transactions);
            throw new Exception("Card is Deactivated, Please active is first");
        }

        if(libraryCard.getCardStatus() == BLOCKED){
            transactions.setTransactionStatus(TransactionStatus.FAILED);
            transactions = transactionRepository.save(transactions);
            throw new Exception("Your card is Blocked, you cant get any book");
        }

        if(libraryCard.getNoOfBooksIssued() == bookMaxLimit) {
            transactions.setTransactionStatus(TransactionStatus.FAILED);
            transactions = transactionRepository.save(transactions);
            throw new Exception("Maximum number of books allocated already");
        }
        /*  All the failed cases and invalid scenarios are over here  */


        transactions.setTransactionStatus(TransactionStatus.SUCCESS);

//      Update variables status
        book.setAvailable(false);
        libraryCard.setNoOfBooksIssued(libraryCard.getNoOfBooksIssued()+1);
        String totalBooks = "";
        if(libraryCard.getBooksList() == null){
            totalBooks+=book.getTitle()+",";
            libraryCard.setBooksList(totalBooks);
        }else {
             totalBooks += libraryCard.getBooksList()+book.getTitle()+",";
            libraryCard.setBooksList(totalBooks);
        }



//      this is  unidirectional Mapping
        transactions.setBook321(book);
        transactions.setLibraryCard321(libraryCard);

//      this custom transWithId to avoid the duplicate entres
        Transactions newTransactionWithId = transactionRepository.save(transactions);

        book.getTransactionsList().add(newTransactionWithId);
        libraryCard.getTransactionsList().add(newTransactionWithId);

        bookRepository.save(book);
        libraryCardRepository.save(libraryCard);

        return "Transaction Completed Successfully and Book Allocated to "+libraryCard.getStudent123123().getName()+"";
    }

    public String returnBook(Integer cardId, Integer bookId) throws Exception{

//        Validations
        if(!libraryCardRepository.existsById(cardId)){
            throw new Exception("This student/Library Card is not issued by this Library");
        }
        Optional<LibraryCard> optionalLibraryCard = libraryCardRepository.findById(cardId);
        LibraryCard libraryCard = optionalLibraryCard.get();



        if(!bookRepository.existsById(bookId)){
            throw new Exception("This book is not of our Library");
        }
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.get();

        boolean isExist = false;
        String newBookList = "";
        String[] totalBooks = libraryCard.getBooksList().split(",");
        for(String str: totalBooks){
            if(str.equals(book.getTitle())){
                isExist = true;
            }else{
                newBookList += str+",";
            }
        }
        if(isExist == false){
            throw new Exception("This book is not issued to "+libraryCard.getStudent123123().getName()+"");
        }

        libraryCard.setBooksList(newBookList);

//       List<Transactions> transactionsList = transactionRepository.findTransactionsByBookAndLibraryCardAndTransactionStatusAndTransactionType(book,libraryCard,TransactionStatus.SUCCESS,TransactionType.ISSUE);
        List<Transactions> temTransactionsList = transactionRepository.findAll();
        List<Transactions> transactionsList = new ArrayList<>();

        for(Transactions transactions: temTransactionsList){
            if(transactions.getBook321() != null && transactions.getLibraryCard321() != null && transactions.getBook321().equals(book) && transactions.getLibraryCard321().equals(libraryCard)
                && transactions.getTransactionType().equals(TransactionType.ISSUE) && transactions.getTransactionStatus().equals(TransactionStatus.SUCCESS)){
                transactionsList.add(transactions);
            }
        }

        Transactions latestTransaction = transactionsList.get(transactionsList.size()-1);
        Date issueDate = latestTransaction.getCreatedAt();

        long miliSecondTime = Math.abs(System.currentTimeMillis() - issueDate.getTime());

        long no_of_days_issue = TimeUnit.DAYS.convert(miliSecondTime,TimeUnit.MILLISECONDS);

        int fineAmmount = 0;
        if(no_of_days_issue > 15){
            fineAmmount = (int) (no_of_days_issue-15)*5;
        }



        Transactions transactions = new Transactions(TransactionType.RETURN,TransactionStatus.SUCCESS,fineAmmount);

        book.setAvailable(true);
        libraryCard.setNoOfBooksIssued(libraryCard.getNoOfBooksIssued()-1);

        transactions.setBook321(book);
        transactions.setLibraryCard321(libraryCard);

        Transactions newTransactionWithId = transactionRepository.save(transactions);

        book.getTransactionsList().add(newTransactionWithId);
        libraryCard.getTransactionsList().add((newTransactionWithId));



        bookRepository.save(book);
        libraryCardRepository.save(libraryCard);

        return "Book has been return successfully by "+ libraryCard.getStudent123123().getName()+" and the fine ammount is $"+fineAmmount+"";
    }

    public List<GetTransactionDetailsRespDto> findTransciontDetails(Integer cardNo) throws Exception{
        if(!libraryCardRepository.existsById(cardNo)){
            throw new LibraryCardNotFoundException("This card not Exists");
        }

        List<Transactions> transactionsList = transactionRepository.findTransactionByLibraryCard321CardNo(cardNo);
        if(transactionsList.isEmpty()){
            throw new Exception("There is no transaction found related to this card");
        }

        List<GetTransactionDetailsRespDto> respDtos = new ArrayList<>();
        for (Transactions transactions: transactionsList){
            GetTransactionDetailsRespDto obj = new GetTransactionDetailsRespDto(transactions.getTransactionId(),transactions.getLibraryCard321().getStudent123123().getName(),
                                                    transactions.getCreatedAt(),transactions.getTransactionType(),transactions.getTransactionStatus());
            respDtos.add(obj);
        }
        return respDtos;
    }
}
