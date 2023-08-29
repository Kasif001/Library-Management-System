package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Models.Student;
import com.example.librarymanagementsystem.Repositries.StudentRepository;
import com.example.librarymanagementsystem.RequestDtos.AddStudentReqDto;
import com.example.librarymanagementsystem.ResponseDto.getLibraryCardRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class StudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JavaMailSender emailSender;

    public String addStudent(AddStudentReqDto addStudentReqDto){

        Student student = Student.builder()
                .email(addStudentReqDto.getEmail())
                .cardStatus(addStudentReqDto.getCardStatus())
                .name(addStudentReqDto.getName())
                .department(addStudentReqDto.getDepartment())
                .gender(addStudentReqDto.getGender())
                .build();
        studentRepository.save(student);

        //Sending mail to user functionality
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String body = "Hi "+student.getName()+"\n"+
                "Sign in successful at Library Management website.Explore different types of books here!";

        mailMessage.setSubject("!Welcome To College Library Website!");
        mailMessage.setFrom("springacciojob@gmail.com");
        mailMessage.setTo(student.getEmail());
        mailMessage.setText(body);

        emailSender.send(mailMessage);

        return "Student named "+student.getName()+" add Successfully and your roll no : "+student.getRollNo()+"";

    }

    public getLibraryCardRespDto findCard(Integer rollNo) throws Exception{

        if(!studentRepository.existsById(rollNo)){
            throw new Exception("Roll No is invalid");
        }

        Student student = studentRepository.findById(rollNo).get();
        if(student.getLibraryCard() == null){
            throw new Exception("Library card is not issued to this student");
        }

        LibraryCard libraryCard = student.getLibraryCard();
        getLibraryCardRespDto respDto = new getLibraryCardRespDto(libraryCard.getCardNo(),libraryCard.getCardStatus(),libraryCard.getNoOfBooksIssued());
        return respDto;
    }


    public String findDepartment(Integer id) throws Exception {
        if(!studentRepository.existsById(id)){
            throw new Exception("This Student not exists");
        }
        Student student = studentRepository.getReferenceById(id);
        String depart = student.getDepartment().toString();
        return depart;
    }
}
