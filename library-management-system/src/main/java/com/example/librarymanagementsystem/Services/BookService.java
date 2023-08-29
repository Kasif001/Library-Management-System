package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.CustomExceptions.BookNotAvailableException;
import com.example.librarymanagementsystem.Enums.Genre;
import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.Models.Book;
import com.example.librarymanagementsystem.Repositries.AuthorRepository;
import com.example.librarymanagementsystem.Repositries.BookRepository;
import com.example.librarymanagementsystem.RequestDtos.BookRequestDto;
import com.example.librarymanagementsystem.ResponseDto.AddBookResponseDto;
import com.example.librarymanagementsystem.ResponseDto.BookResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private JavaMailSender emailSender;

    public AddBookResponseDto addBook(BookRequestDto bookRequestDto) throws Exception{

        Optional<Author> optionalAuthor = authorRepository.findById(bookRequestDto.getAuthorId());
        if(!optionalAuthor.isPresent()){
            throw new Exception("Author is not registered");
        }
        Author author = optionalAuthor.get();

        //making a book obj and setting all values by constructor
        Book book = new Book(bookRequestDto.getTitle(),bookRequestDto.getPages(),bookRequestDto.isAvailable(),bookRequestDto.getGenre(),bookRequestDto.getPublicationDate(),bookRequestDto.getPrice(),author);

        book.setAuthor(author);
        author.getBookList().add(book);
        authorRepository.save(author);

        AddBookResponseDto responseDto = new AddBookResponseDto(book.getTitle(),author.getName());

        //Sending mail to user functionality
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String body = "Congratulation "+author.getName()+"\n"+
                "Your book "+book.getTitle()+" has been add successfully to the library,Now folks can Explore it! ";

        mailMessage.setSubject("Regarding the book registration");
        mailMessage.setFrom("springacciojob@gmail.com");
        mailMessage.setTo(author.getEmail());
        mailMessage.setText(body);

        emailSender.send(mailMessage);
        return responseDto;

    }

    public BookResponseDto getBookByName(String bookName) throws Exception{

        Book book = bookRepository.findBookByTitle(bookName);
        if(book == null){
            throw new BookNotAvailableException("This book not available in this library");
        }

        BookResponseDto responseDto = new BookResponseDto(book.getTitle(),book.getAuthor().getName(),book.getGenre(),
                                          book.getPages(),book.getPublicationDate(),book.getPrice());

        return responseDto;
    }

    public List<BookResponseDto> getAllBooksByGenre(Genre genre){
        List<BookResponseDto> responseDtos = new ArrayList<>();

        List<Book> bookslist = bookRepository.findBooksByGenre(genre);

        for(Book book: bookslist){
            BookResponseDto bookResponseDto = new BookResponseDto(book.getTitle(),book.getAuthor().getName(),
                                                  book.getGenre(),book.getPages(), book.getPublicationDate(),book.getPrice());

            responseDtos.add(bookResponseDto);
        }

        return responseDtos;
    }
}
