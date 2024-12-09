package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "admin_dashboard")
public class AdminDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private Integer mcqScore;

    private Integer mcqTotalQuestions;

    private Integer aptitudeScore;

    private Integer aptitudeTotalQuestions;

    private Integer codingScore;

    private Integer codingTotalTestCases;

    private LocalDate date; // To store the date of the test results
}
