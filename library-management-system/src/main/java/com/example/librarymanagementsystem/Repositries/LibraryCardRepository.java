package com.example.librarymanagementsystem.Repositries;

import com.example.librarymanagementsystem.Models.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard,Integer> {
}
