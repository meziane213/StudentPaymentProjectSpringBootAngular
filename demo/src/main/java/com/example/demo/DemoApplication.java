package com.example.demo;

import com.example.demo.entities.Payment;
import com.example.demo.entities.PaymentStatus;
import com.example.demo.entities.PaymentType;
import com.example.demo.entities.Student;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID()
                    .toString()).firstName("Zizou").lastName("bsd").code("112233").programId("MIAGE").build());
            studentRepository.save(Student.builder().lastName("bsd").id(UUID.randomUUID()
                    .toString()).firstName("yanis").lastName("bsd").code("186988").programId("BDD").build());
            studentRepository.save(Student.builder().lastName("bsd").id(UUID.randomUUID()
                    .toString()).firstName("nassim").code("112255").programId("RESEAU").build());
            studentRepository.save(Student.builder().lastName("bsd").id(UUID.randomUUID()
                    .toString()).firstName("chervuv").code("1523").programId("MEDCINE").build());

            PaymentType[] paymentTypes=PaymentType.values();
            Random random =new Random();
            studentRepository.findAll().forEach(student -> {
                int index= random.nextInt(paymentTypes.length);
                for(int i=0;i<=10;i++) {
                    Payment payment= Payment.builder().amount(1000+(int)(Math.random()+20000)).type(paymentTypes[index])
                            .status(PaymentStatus.CREATED).date(LocalDate.now()).student(student)
                    .build();
                    paymentRepository.save(payment);
                }
            });
        };
    }

}
