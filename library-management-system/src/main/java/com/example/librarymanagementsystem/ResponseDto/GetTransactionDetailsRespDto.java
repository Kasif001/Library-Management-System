package com.example.librarymanagementsystem.ResponseDto;

import com.example.librarymanagementsystem.Enums.TransactionStatus;
import com.example.librarymanagementsystem.Enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetTransactionDetailsRespDto {
    private Integer transactionId;
    private String studentName;
    private Date createdAt;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;

}
