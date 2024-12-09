package com.example.demo.serviceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AptitudeService {

    private static final String apiKey = "AIzaSyDoIi0erIXTQp6A7gtc4cTFil4riqspqLY";
    private static final String APTITUDE_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=";

    private String lastGeneratedMcqs,mcqsWithAnswers;
    private Map<String, String> aptitudeAnswerKey  = new HashMap<>(); // To store the correct answers


    public String generateAptitudeQuestions(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Request to generate aptitude questions
        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"Generate 10 aptitude questions with multiple choices  based on maths, calender, clock, probability, problem on ages etc And provide the answers of all the questions separately at the end after all the questions like for example 1) If one-third of one-fourth of a number is 15, then three-tenth of that number is:\n" +
                "a) 35\n" +
                "b) 36\n" +
                "c) 45\n" +
                "d) 54\n" +
                "2) The sum of ages of 5 children born at the intervals of 3 years each is 50 years. What is the age of the youngest child?" +
                "a) 4 years\n" +
                "b) 8 years\n" +
                "c) 10 years\n" +
                "d) None of these\n" +
                "Answer: 1. d) 54\n" +
                "2. a) 4 years\n" +
                "The answers must always be formatted consistently starting with '## Answers:' followed by the question number and answer.\n"+
                "Out of all 10 questions 1 question should be easy, 5 questions should be of moderate level, 2 should be tough and 2 should be really tough. And questions should be mixed without mentioning which question is from which topic Also the format should be very specific first all the questions with their options then all the answers with question number respectively.\"}]}]}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(APTITUDE_API_URL + apiKey, HttpMethod.POST, requestEntity, String.class);
            System.out.println("Gemini API Response: " + response.getBody());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
    // Extract the generated questions and options (including answers)
    String mcqsWithAnswers = root.at("/candidates/0/content/parts/0/text").asText();
            System.out.println(mcqsWithAnswers);

    String[] sections = mcqsWithAnswers.split("## Answers:");
            if (sections.length < 2) {
        throw new RuntimeException("Invalid MCQ format: Answers section not found");
    }

    // Process only the questions section
    String questionsSection = sections[0].trim();
    String answersSection = sections[1].trim();

    // Store normalized questions
    lastGeneratedMcqs = normalizeQuestionsFormat(questionsSection);

    // Populate the backend answer key for comparison
    populateAnswerKey(mcqsWithAnswers);

    // Return only questions and options to the frontend (without answers)
            return lastGeneratedMcqs;

} catch (Exception e) {
        return "Error generating MCQs: " + e.getMessage();
        }
    }

    private String normalizeQuestionsFormat(String questionsOnly) {
        // Remove any extra formatting markers
        String cleanedQuestions = questionsOnly
                .replaceAll("##\\s*", "")  // Remove ## markers
                .replaceAll("\n{2,}", "\n")  // Reduce multiple newlines
                .trim();

        // Ensure each question starts on a new line with a number
        cleanedQuestions = cleanedQuestions.replaceAll("(\\d+\\.)", "\n$1");

        // Ensure consistent spacing between questions
        cleanedQuestions = cleanedQuestions.replaceAll("\n(\\w)", "\n\n$1");

        return cleanedQuestions;
    }


    public String getLastGeneratedAptitudeQuestions() {
        return lastGeneratedMcqs != null ? lastGeneratedMcqs : "No MCQs available";
    }

    private void populateAnswerKey(String lastGeneratedAptitudeQuestions) {
        String answersSection = lastGeneratedAptitudeQuestions.split("## Answers:")[1].trim();
        String[] answerLines = answersSection.split("\n");

        for (String line : answerLines) {
            if (!line.isEmpty()) {
                String[] parts = line.split("\\)");
                String questionNumber = parts[0].trim();
                String correctAnswer = parts[1].replace("**", "").trim().split("\\)")[0].trim().toLowerCase(); // Remove '**'

                aptitudeAnswerKey.put(questionNumber, correctAnswer); // Store question number and correct answer
            }
        }
        System.out.println("Correct Answer Key: " + aptitudeAnswerKey);
    }

    public Map<String, String> checkAnswers(Map<String, String> userAnswers) {
        Map<String, String> results = new HashMap<>();
        int score = 0;
        int totalQuestions = aptitudeAnswerKey.size();

        System.out.println("User Answers: " + userAnswers);

        for (Map.Entry<String, String> entry : userAnswers.entrySet()) {
            String questionNumber = entry.getKey().trim();
            String userAnswer = entry.getValue().trim().toLowerCase();

            if (userAnswer.contains(")")) {
                userAnswer = userAnswer.split("\\)")[0].trim().toLowerCase(); // Extract just the letter
            }

            if (aptitudeAnswerKey.containsKey(questionNumber) && aptitudeAnswerKey.get(questionNumber).equals(userAnswer)) {
                score++;
            }
        }
        results.put("score", String.valueOf(score));
        results.put("totalQuestions", String.valueOf(totalQuestions));
        return results;
    }

    public String resetGeminiContext() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Thank you prompt to reset Gemini's context
        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"Thank you for generating the aptitude questions. Please reset all memory related to this session.\"}]}]}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        try {
            // Sending the request to reset Gemini's memory
            ResponseEntity<String> response = restTemplate.exchange(APTITUDE_API_URL + apiKey, HttpMethod.POST, requestEntity, String.class);
            return "Memory reset successfully";
        } catch (Exception e) {
            return "Error resetting Gemini memory: " + e.getMessage();
        }
    }
}