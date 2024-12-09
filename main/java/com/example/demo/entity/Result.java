package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "mcq_score")
    private int mcqScore;

    @Column(name = "mcq_total_questions")
    private int mcqTotalQuestions;

    @Column(name = "aptitude_score")
    private int aptitudeScore;

    @Column(name = "aptitude_total_questions")
    private int aptitudeTotalQuestions;

    @Column(name = "coding_score")
    private int codingScore;

    @Column(name = "coding_total_questions")
    private int codingTotalQuestions;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public Result() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMcqScore() {
        return mcqScore;
    }

    public void setMcqScore(int mcqScore) {
        this.mcqScore = mcqScore;
    }

    public int getMcqTotalQuestions() {
        return mcqTotalQuestions;
    }

    public void setMcqTotalQuestions(int mcqTotalQuestions) {
        this.mcqTotalQuestions = mcqTotalQuestions;
    }

    public int getAptitudeScore() {
        return aptitudeScore;
    }

    public void setAptitudeScore(int aptitudeScore) {
        this.aptitudeScore = aptitudeScore;
    }

    public int getAptitudeTotalQuestions() {
        return aptitudeTotalQuestions;
    }

    public void setAptitudeTotalQuestions(int aptitudeTotalQuestions) {
        this.aptitudeTotalQuestions = aptitudeTotalQuestions;
    }

    public int getCodingScore() {
        return codingScore;
    }

    public void setCodingScore(int codingScore) {
        this.codingScore = codingScore;
    }

    public int getCodingTotalQuestions() {
        return codingTotalQuestions;
    }

    public void setCodingTotalQuestions(int codingTotalQuestions) {
        this.codingTotalQuestions = codingTotalQuestions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
