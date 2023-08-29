package com.example.librarymanagementsystem.Repositries;

import com.example.librarymanagementsystem.Enums.Genre;
import com.example.librarymanagementsystem.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

//  2nd way of query to fetch data from table using inbuild functions of Hibernate
    List<Book> findBooksByGenre(Genre genre);

    Book findBookByTitle(String title);

}
