package com.example.demo.dtos;

import com.example.demo.entities.PaymentType;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NewPaymentDto {


    private LocalDate date;
    private double amount;
    private PaymentType type;
    private String studentCode;
}
