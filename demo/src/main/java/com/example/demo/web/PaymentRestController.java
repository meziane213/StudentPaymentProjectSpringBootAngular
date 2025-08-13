package com.example.demo.web;

import com.example.demo.dtos.NewPaymentDto;
import com.example.demo.entities.Payment;
import com.example.demo.entities.PaymentStatus;
import com.example.demo.entities.PaymentType;
import com.example.demo.entities.Student;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PaymentRestController {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @GetMapping(path="/payments")
    public List<Payment> allPayments(){
        return paymentRepository.findAll();
    }
    @GetMapping(path="/students/{code}/payments")
    public List<Payment> allPaymentsByStudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping(path="/payments/byStatus")
    public List<Payment> allPaymentsByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }

    @GetMapping(path="/payments/byType")
    public List<Payment> allPaymentsByType(@RequestParam PaymentType type){
        return paymentRepository.findByType(type);
    }


    @GetMapping(path="/payment/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentRepository.findById(id).get();
    }

    @GetMapping(path = "/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }


    @GetMapping(path="/students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }

    @GetMapping(path="/studentsByProgramID")
    public List<Student> getStudentsByProgramId(@RequestParam String programId){
        return studentRepository.findByProgramId(programId);
    }

   @PutMapping("payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status,@PathVariable Long id){
        Payment payment= paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);

    }

    @PostMapping(path = "/payments",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam("file") MultipartFile file,NewPaymentDto newPaymentDto) throws IOException {
     return this.paymentService.savePayment(file,newPaymentDto);
    }

    @GetMapping(path = "/paymentFile/{idPayment}",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long idPayment) throws IOException {
        String file=paymentRepository.findById(idPayment).get().getFile();
        return Files.readAllBytes(Path.of(URI.create(file)));
    }
}
