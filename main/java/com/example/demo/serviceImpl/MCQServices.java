//package com.example.demo.serviceImpl;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class MCQService {
//
//    private static final String apiKey = "AIzaSyDr5cHw53LGSueVJZOl0sozmDZ3pH5yrW8";
//    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=";
//
//    private String lastGeneratedMcqs;
//    private Map<String, String> answerKey = new HashMap<>(); // To store the correct answers
//
//    public String generateMCQs(String extractedText) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//
//        // Request to generate MCQs with questions and answers
//        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"Generate 30 multiple-choice questions based on this content " + extractedText + " And provide the answers of all the questions separately at the end after all the questions like this example 1. Which programming language is NOT mentioned in Aryaa's skills?\n" +
//                " a) C++\n" +
//                " b) Python\n" +
//                " c) C#\n" +
//                " d) Java\n" +
//                "2. Which database technology is Aryaa proficient in?\n" +
//                " a) MongoDB\n" +
//                " b) Oracle\n" +
//                " c) PostgreSQL\n" +
//                " d) MySQL.\n" +
//                "3. What is the name of the online proctoring system Aryaa developed?\n" +
//                " a) Eduguard\n" +
//                " b) SafeExam\n" +
//                " c) ProctorSystem\n" +
//                " d) OnlineProctor\n" +
//                "Answer: 1. c) C#\n" +
//                "2. d) MySQL\n" +
//                "3. a) Eduguard\n" +
//                "So like this first all the questions with their options should come and then at the end answers of the questions respectively\"}]}]}";
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(GEMINI_API_URL + apiKey, HttpMethod.POST, requestEntity, String.class);
//
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode root = mapper.readTree(response.getBody());
//
//            // Extract the generated questions and options (including answers)
//            String mcqsWithAnswers = root.at("/candidates/0/content/parts/0/text").asText();
//
//            // Remove the section starting with "Answer:" and everything after it
//            lastGeneratedMcqs = mcqsWithAnswers.split("Answers:")[0].trim();
//
//            // Populate the backend answer key for comparison
//            populateAnswerKey(mcqsWithAnswers);
//
//            // Return only questions and options to the frontend (without answers)
//            return lastGeneratedMcqs;
//
//        } catch (Exception e) {
//            return "Error generating MCQs: " + e.getMessage();
//        }
//    }
//
//    public String getLastGeneratedMCQs() {
//        return lastGeneratedMcqs != null ? lastGeneratedMcqs : "No MCQs available";
//    }
//
//    private void populateAnswerKey(String mcqsWithAnswers) {
//        String answersSection = mcqsWithAnswers.split("Answers:")[1].trim();
//        String[] answerLines = answersSection.split("\n");
//
//        for (String line : answerLines) {
//            if (!line.isEmpty()) {
//                String[] parts = line.split("\\.");
//                String questionNumber = parts[0].trim();
//                String correctAnswer = parts[1].replace("**", "").trim().split("\\)")[0].trim().toLowerCase(); // Remove '**'
//
//                answerKey.put(questionNumber, correctAnswer); // Store question number and correct answer
//            }
//        }
//        System.out.println("Correct Answer Key: " + answerKey);
//    }
//
//    public Map<String, String> checkAnswers(Map<String, String> userAnswers) {
//        Map<String, String> results = new HashMap<>();
//        int score = 0;
//        int totalQuestions = answerKey.size();
//
//        System.out.println("User Answers: " + userAnswers);
//
//        for (Map.Entry<String, String> entry : userAnswers.entrySet()) {
//            String questionNumber = entry.getKey().trim();
//            String userAnswer = entry.getValue().trim().toLowerCase();
//
//            if (userAnswer.contains(")")) {
//                userAnswer = userAnswer.split("\\)")[0].trim().toLowerCase(); // Extract just the letter
//            }
//
//            if (answerKey.containsKey(questionNumber) && answerKey.get(questionNumber).equals(userAnswer)) {
//                score++;
//            }
//        }
//        results.put("score", String.valueOf(score));
//        results.put("totalQuestions", String.valueOf(totalQuestions));
//        return results;
//    }
//
//    public String resetGeminiContext() {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//
//        // Thank you prompt to reset Gemini's context
//        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"Thank you for generating the aptitude questions. Please reset all memory related to this session.\"}]}]}";
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
//
//        try {
//            // Sending the request to reset Gemini's memory
//            ResponseEntity<String> response = restTemplate.exchange(GEMINI_API_URL + apiKey, HttpMethod.POST, requestEntity, String.class);
//            return "Memory reset successfully";
//        } catch (Exception e) {
//            return "Error resetting Gemini memory: " + e.getMessage();
//        }
//    }
//
//}
