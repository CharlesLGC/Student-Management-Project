package com.charles.student.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "students")
public class Student {

    // student fields
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long studentId;

    @NotBlank(message = "Please insert your name")
    @Column(nullable = false)
    private String name;

    @Email(message = "Please insert a valid email")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Please insert your phone number")
    private String phone;

    @NotBlank(message = "Please insert your address")
    @Column(nullable = false)
    private String address;

    @Past(message = "Please insert a valid birth date")
    @NotNull(message = "Please insert your birth date")
    @Column(name = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public Student() {
    }

    public Student(String name, String email, String phone, String address, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
    }

    // Getters and Setters
    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
