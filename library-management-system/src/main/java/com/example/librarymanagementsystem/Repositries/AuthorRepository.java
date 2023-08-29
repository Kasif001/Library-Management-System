package com.example.librarymanagementsystem.Repositries;

import com.example.librarymanagementsystem.Models.Author;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

//    3rd way to write our custom quries but i have not writen api for it yet
    @Query(value = "select * from author where age >= :enterAge;",nativeQuery = true)
    List<Author> findAuthorGraterThanAge(Integer enterAge);
}
