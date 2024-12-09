//package com.example.demo.serviceImpl;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class MCQService {
//
//    private static final String apiKey = "AIzaSyDoIi0erIXTQp6A7gtc4cTFil4riqspqLY";
//    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=";
//
//    private String lastGeneratedMcqs, mcqs;
//    private Map<String, String> answerKey = new HashMap<>(); // To store the correct answers
//
//    public String generateMCQs(String extractedText) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//
//
//        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"Generate 30 multiple-choice questions And provide the answers of all the questions separately at the end after all the questions for example\n" +
//                "1. Automatic type conversion is possible in which of the possible cases?\n" +
//                " a) Byte to int\n" +
//                " b) Int to long\n" +
//                " c) Long to int\n" +
//                " d) Short to int\n" +
//                "2. Find the output of the following code.\n" +
//                "\n" +
//                "int Integer = 24;\n" +
//                "char String  = ‘I’;\n" +
//                "System.out.print(Integer);\n" +
//                "System.out.print(String);\n" +
//                " a) Compile error\n" +
//                " b) Throws exception\n" +
//                " c) 1\n" +
//                " d) 2 4 1\n" +
//                "3. Find the output of the following program.\n" +
//                "\n" +
//                "public class Solution{\n" +
//                "       public static void main(String[] args){\n" +
//                "                     short x = 10;\n" +
//                "                     x =  x * 5;\n" +
//                "                     System.out.print(x);\n" +
//                "       }\n" +
//                "}" +
//                " a) 50\n" +
//                " b) 10\n" +
//                " c) Compile error\n" +
//                " d) Exception\n" +
//                "Answer: 1. b) Int to long\n" +
//                "2. d) 2 4 1\n" +
//                "3. c) Comile error\n" +
//                "So like this first all the questions with their options should come and then at the end answers of the questions respectively" +
//                "Similary These examples also" +
//                "1. In which of the following is memory allocated for the objects?\n" +
//                "a) RAM\n" +
//                "b) ROM\n" +
//                "c) Cache\n" +
//                "d) HDD\n" +
//                "2. On what basis is it determined, when a variable comes into existence in memory?\n" +
//                "a) Data type\n" +
//                "b) Storage class\n" +
//                "c) Scope\n" +
//                "d) All of the above\n" +
//                "Answer: 1. a) RAM\n" +
//                "2. b) Storage class\n" +
//                "The answers must always be formatted consistently starting with '## Answers:' followed by the question number and answer.\n"+
//                "Create similar questions for coding languages and frameworks like MERN, React, and Python, machine learning libraries like panda, numpy, tensorflow etc and things like core concepts, fundamentals and codes etc And questions should be mixed without mentioning which question is from which topic Also the format should be very specific first all the questions with their options then all the answers with question number respectively.\"}]}]}";
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
//            System.out.println(mcqsWithAnswers);
//
//            String[] sections = mcqsWithAnswers.split("## Answers:");
//            if (sections.length < 2) {
//                throw new RuntimeException("Invalid MCQ format: Answers section not found");
//            }
//
//            // Process only the questions section
//            String questionsSection = sections[0].trim();
//            String answersSection = sections[1].trim();
//
//            // Store normalized questions
//            lastGeneratedMcqs = normalizeQuestionsFormat(questionsSection);
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
//    private String normalizeQuestionsFormat(String questionsOnly) {
//        // Remove any extra formatting markers
//        String cleanedQuestions = questionsOnly
//                .replaceAll("##\\s*", "")  // Remove ## markers
//                .replaceAll("\n{2,}", "\n")  // Reduce multiple newlines
//                .trim();
//
//        // Ensure each question starts on a new line with a number
//        cleanedQuestions = cleanedQuestions.replaceAll("(\\d+\\.)", "\n$1");
//
//        // Ensure consistent spacing between questions
//        cleanedQuestions = cleanedQuestions.replaceAll("\n(\\w)", "\n\n$1");
//
//        return cleanedQuestions;
//    }
//
//    public String getLastGeneratedMCQs() {
//        return lastGeneratedMcqs != null ? lastGeneratedMcqs : "No MCQs available";
//    }
//
//
//    private void populateAnswerKey(String mcqsWithAnswers) {
//        String answersSection = mcqsWithAnswers.split("## Answers:")[1].trim();
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
//        System.out.println("Correct Answers: " + answerKey);
//
//        for (Map.Entry<String, String> entry : userAnswers.entrySet()) {
//            String questionNumber = entry.getKey().trim();
//            String userAnswer = entry.getValue().trim().toLowerCase();
//
//            if (userAnswer.contains(")")) {
//                userAnswer = userAnswer.split("\\)")[0].trim().toLowerCase();
//            }
//
//            // Debug print
//            System.out.println("Checking Q" + questionNumber +
//                    ": User Answer = " + userAnswer +
//                    ", Correct Answer = " + answerKey.get(questionNumber));
//
//            if (answerKey.containsKey(questionNumber) &&
//                    answerKey.get(questionNumber).equals(userAnswer)) {
//                score++;
//                System.out.println("Correct! Score incremented to " + score);
//            }
//        }
//
//        results.put("score", String.valueOf(score));
//        results.put("totalQuestions", String.valueOf(totalQuestions));
//
//        System.out.println("Final Results: " + results);
//        return results;
//    }
//
//
//    public String resetGeminiContext() {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//
//        // Thank you prompt to reset Gemini's context
//        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"Thank you for generating the aptitude questions. Please reset all memory.\"}]}]}";
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
//}





















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
public class MCQService {

    private static final String apiKey = "AIzaSyDoIi0erIXTQp6A7gtc4cTFil4riqspqLY";
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=";

    private String lastGeneratedMcqs, mcqs;
    private Map<String, String> answerKey = new HashMap<>(); // To store the correct answers

    public String generateMCQs(String extractedText) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");


        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"Generate 30 multiple-choice questions And provide the answers of all the questions separately at the end after all the questions for example\n" +
                "1. Automatic type conversion is possible in which of the possible cases?\n" +
                " a) Byte to int\n" +
                " b) Int to long\n" +
                " c) Long to int\n" +
                " d) Short to int\n" +
                "2. Find the output of the following code.\n" +
                "\n" +
                "int Integer = 24;\n" +
                "char String  = ‘I’;\n" +
                "System.out.print(Integer);\n" +
                "System.out.print(String);\n" +
                " a) Compile error\n" +
                " b) Throws exception\n" +
                " c) 1\n" +
                " d) 2 4 1\n" +
                "3. Find the output of the following program.\n" +
                "\n" +
                "public class Solution{\n" +
                "       public static void main(String[] args){\n" +
                "                     short x = 10;\n" +
                "                     x =  x * 5;\n" +
                "                     System.out.print(x);\n" +
                "       }\n" +
                "}" +
                " a) 50\n" +
                " b) 10\n" +
                " c) Compile error\n" +
                " d) Exception\n" +
                "Answer: 1. b) Int to long\n" +
                "2. d) 2 4 1\n" +
                "3. c) Comile error\n" +
                "So like this first all the questions with their options should come and then at the end answers of the questions respectively" +
                "Similary These examples also" +
                "1. In which of the following is memory allocated for the objects?\n" +
                "a) RAM\n" +
                "b) ROM\n" +
                "c) Cache\n" +
                "d) HDD\n" +
                "2. On what basis is it determined, when a variable comes into existence in memory?\n" +
                "a) Data type\n" +
                "b) Storage class\n" +
                "c) Scope\n" +
                "d) All of the above\n" +
                "Answer: 1. a) RAM\n" +
                "2. b) Storage class\n" +
                "The answers must always be formatted consistently starting with '## Answers:' followed by the question number and answer.\n"+
                "Create similar questions for coding languages and frameworks like MERN, React, and Python, machine learning libraries like panda, numpy, tensorflow etc and things like core concepts, fundamentals and codes etc And questions should be mixed without mentioning which question is from which topic Also the format should be very specific first all the questions with their options then all the answers with question number respectively.\"}]}]}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(GEMINI_API_URL + apiKey, HttpMethod.POST, requestEntity, String.class);

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


    public String getLastGeneratedMCQs() {
        return lastGeneratedMcqs != null ? lastGeneratedMcqs : "No MCQs available";
    }


    private void populateAnswerKey(String mcqsWithAnswers) {
        String answersSection = mcqsWithAnswers.split("## Answers:")[1].trim();
        String[] answerLines = answersSection.split("\n");

        for (String line : answerLines) {
            if (!line.isEmpty()) {
                String[] parts = line.split("\\.");
                String questionNumber = parts[0].trim();
                String correctAnswer = parts[1].replace("**", "").trim().split("\\)")[0].trim().toLowerCase(); // Remove '**'

                answerKey.put(questionNumber, correctAnswer); // Store question number and correct answer
            }
        }
        System.out.println("Correct Answer Key: " + answerKey);
    }

    public Map<String, String> checkAnswers(Map<String, String> userAnswers) {
        Map<String, String> results = new HashMap<>();
        int score = 0;
        int totalQuestions = answerKey.size();

        System.out.println("User Answers: " + userAnswers);
        System.out.println("Correct Answers: " + answerKey);

        for (Map.Entry<String, String> entry : userAnswers.entrySet()) {
            String questionNumber = entry.getKey().trim();
            String userAnswer = entry.getValue().trim().toLowerCase();

            if (userAnswer.contains(")")) {
                userAnswer = userAnswer.split("\\)")[0].trim().toLowerCase();
            }

            // Debug print
            System.out.println("Checking Q" + questionNumber +
                    ": User Answer = " + userAnswer +
                    ", Correct Answer = " + answerKey.get(questionNumber));

            if (answerKey.containsKey(questionNumber) &&
                    answerKey.get(questionNumber).equals(userAnswer)) {
                score++;
                System.out.println("Correct! Score incremented to " + score);
            }
        }

        results.put("score", String.valueOf(score));
        results.put("totalQuestions", String.valueOf(totalQuestions));

        System.out.println("Final Results: " + results);
        return results;
    }


    public String resetGeminiContext() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Thank you prompt to reset Gemini's context
        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"Thank you for generating the aptitude questions. Please reset all memory.\"}]}]}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        try {
            // Sending the request to reset Gemini's memory
            ResponseEntity<String> response = restTemplate.exchange(GEMINI_API_URL + apiKey, HttpMethod.POST, requestEntity, String.class);
            return "Memory reset successfully";
        } catch (Exception e) {
            return "Error resetting Gemini memory: " + e.getMessage();
        }
    }
}





