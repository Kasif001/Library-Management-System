package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.CustomExceptions.LibraryCardNotFoundException;
import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Models.Student;
import com.example.librarymanagementsystem.Repositries.LibraryCardRepository;
import com.example.librarymanagementsystem.Repositries.StudentRepository;
import com.example.librarymanagementsystem.RequestDtos.GenerateLibraryCardReqDto;
import com.example.librarymanagementsystem.ResponseDto.GetStudentByCardRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryCardService {
    @Autowired
    private LibraryCardRepository libraryCardRepository;
    @Autowired
    private StudentRepository studentRepository;

    public String addCards(GenerateLibraryCardReqDto generateLibraryCardReqDto){
        LibraryCard libraryCard = LibraryCard.builder()
                .cardStatus(generateLibraryCardReqDto.getCardStatus())
                .noOfBooksIssued(generateLibraryCardReqDto.getNoOfBooksIssued()).build();

        libraryCardRepository.save(libraryCard);
        return "Card generated successfully. Your card id is : "+libraryCard.getCardNo()+"";
    }

    public String assignToStudent(Integer cardId, Integer rollNo) throws Exception{
        if(!libraryCardRepository.existsById(cardId)){
            throw new Exception("Card not Exist");
        }
        if(!studentRepository.existsById(rollNo)){
            throw new Exception("Student Not Exist");
        }

        Optional<LibraryCard> optionalLibraryCard = libraryCardRepository.findById(cardId);
        LibraryCard libraryCardObj = optionalLibraryCard.get();

        Optional<Student> optionalStudent = studentRepository.findById(rollNo);
        Student studentObj = optionalStudent.get();


//        Now we have to save objects to each other tables to link
        libraryCardObj.setStudent123123(studentObj);
        studentObj.setLibraryCard(libraryCardObj);

//        if we don't save child(libraryCard) and save only parent(student)
//        then it will also work due to cascade attribute

//        libraryCardRepository.save(libraryCardObj);
        studentRepository.save(studentObj);

        return "Library Card No "+libraryCardObj.getCardNo()+" assign to "+studentObj.getName()+" Successfully";

    }

    public GetStudentByCardRespDto getStudent(Integer cardId) throws Exception{

        if(!libraryCardRepository.existsById(cardId)){
            throw new LibraryCardNotFoundException("This card not exists");
        }
        LibraryCard libraryCard = libraryCardRepository.findById(cardId).get();

        if(libraryCard.getStudent123123() == null){
            throw new Exception("This card not issued yet to any student");
        }

        Student student = libraryCard.getStudent123123();
        GetStudentByCardRespDto respDto = new GetStudentByCardRespDto(student.getName(),student.getGender(),student.getDepartment());
        return respDto;
    }


}
