package com.example.demo.services;

import com.example.demo.dtos.NewPaymentDto;
import com.example.demo.entities.Payment;
import com.example.demo.entities.PaymentStatus;
import com.example.demo.entities.PaymentType;
import com.example.demo.entities.Student;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(MultipartFile file, NewPaymentDto newPaymentDto) throws IOException {
        Path folderPath= Paths.get(System.getProperty("user.home"),"enset-data","payments");
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName= UUID.randomUUID().toString();
        Path filePath= Paths.get(System.getProperty("user.home"),"enset-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student=studentRepository.findByCode(newPaymentDto.getStudentCode());
        Payment payment=Payment.builder().date(newPaymentDto.getDate()).type(newPaymentDto.getType()).student(student).status(PaymentStatus.CREATED).amount(newPaymentDto.getAmount()).file(filePath.toUri().toString()).build();
        return paymentRepository.save(payment);
    }
}
