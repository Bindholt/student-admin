package kea.exercise.studentadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;

@SpringBootApplication
public class StudentAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentAdminApplication.class, args);
        System.out.println("Run some code here");
    }
}