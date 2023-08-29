package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.Repositries.AuthorRepository;
import com.example.librarymanagementsystem.RequestDtos.AddAuthorReqDto;
import com.example.librarymanagementsystem.RequestDtos.UpdateNameAndPenNameRequestDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JavaMailSender emailSender;

    public String addAuthor(AddAuthorReqDto addAuthorReqDto) throws Exception{

        List<Author> getAllAuthor = authorRepository.findAll();
        for(Author author: getAllAuthor){
            if(author.getPenName().equals(addAuthorReqDto.getPenName())){
                throw new Exception("This author already exists.");
            }
        }
        Author author = Author.builder()
                .age(addAuthorReqDto.getAge())
                .name(addAuthorReqDto.getName())
                .penName(addAuthorReqDto.getPenName())
                .email(addAuthorReqDto.getEmail())
                .build();

        authorRepository.save(author);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String body = "Hi "+author.getName()+"\n"+
                "Yor are successfully registered to this library";

        mailMessage.setSubject("Regarding Library Registration");
        mailMessage.setFrom("springacciojob@gmail.com");
        mailMessage.setTo(author.getEmail());
        mailMessage.setText(body);

        emailSender.send(mailMessage);

        return "Author id "+author.getAuthorId()+" add Successfully into db";
    }

    public String updation(UpdateNameAndPenNameRequestDto updateNameAndPenNameRequestDto) throws Exception{
        int id = updateNameAndPenNameRequestDto.getId();
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if(!optionalAuthor.isPresent()){
            throw new Exception("Author not exists in db");
        }

        Author author = optionalAuthor.get();
        String newName = updateNameAndPenNameRequestDto.getNewName();
        String newPenName = updateNameAndPenNameRequestDto.getNewPenName();
        author.setName(newName);
        author.setPenName(newPenName);
        authorRepository.save(author);

        return  "Name and Pen-Name updated Successfully";
    }

    public Author getAuthor(Integer authorId) throws Exception{
        if(!authorRepository.existsById(authorId)){
            throw new Exception("Author id: "+authorId+" not exist");
        }
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Author author = optionalAuthor.get();
        return author;
    }
}
