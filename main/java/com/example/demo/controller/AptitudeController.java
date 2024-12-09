//package com.example.demo.controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.example.demo.serviceImpl.AptitudeService;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/aptitude")
//@CrossOrigin(origins = "http://localhost:4200")
//public class AptitudeController {
//
//    @Autowired
//    private AptitudeService aptitudeService;
//
//    @PostMapping("/generate")
//    public ResponseEntity<Map<String, String>> generateMCQs(@RequestBody String prompt) {
//        try {
//
//            // Generate MCQs based on the extracted text
//            String mcqsAndAnswers = aptitudeService.generateAptitudeQuestions(prompt);
//
//            // Prepare the response excluding answers
//            Map<String, String> response = new HashMap<>();
//            response.put("mcqs", mcqsAndAnswers);
//
////             Call the reset method after task completion
//            aptitudeService.resetGeminiContext();
//
//            // Return the generated MCQs in the response
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            // Handle any exceptions and return an error response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.singletonMap("error", "Failed to generate MCQs"));
//        }
//    }
//
//    @GetMapping("/questions")
//    public ResponseEntity<Map<String, String>> getMCQs() {
//        try {
//            String mcqs = aptitudeService.getLastGeneratedAptitudeQuestions();
//            return ResponseEntity.ok(Collections.singletonMap("mcqs", mcqs));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.singletonMap("error", "Failed to fetch MCQs"));
//        }
//    }
//
//    @PostMapping("/submit")
//    public ResponseEntity<Map<String, String>> submitAnswers(@RequestBody Map<String, String> userAnswers) {
//        try {
//            // Compare the user's answers with the correct answers
//            Map<String, String> results = aptitudeService.checkAnswers(userAnswers);
//
//            // Return the results
//            return ResponseEntity.ok(results);
//
//        } catch (Exception e) {
//            // Handle any exceptions and return an error response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.singletonMap("error", "Failed to submit answers"));
//        }
//    }
//}




package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.serviceImpl.AptitudeService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/aptitude")
@CrossOrigin(origins = "http://localhost:4200")
public class AptitudeController {

    @Autowired
    private AptitudeService aptitudeService;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateAptitude(@RequestBody String prompt) {
        try {
            String aptitudeQuestions = aptitudeService.generateAptitudeQuestions(prompt);
            Map<String, String> response = new HashMap<>();
            response.put("aptitudeQuestions", aptitudeQuestions);

            // Call the reset method after task completion
            aptitudeService.resetGeminiContext();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to generate aptitude questions"));
        }
    }

    @GetMapping("/questions")
    public ResponseEntity<Map<String, String>> getAptitudeQuestions() {
        try {
            String questions = aptitudeService.getLastGeneratedAptitudeQuestions();
            return ResponseEntity.ok(Collections.singletonMap("aptitudeQuestions", questions));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to fetch aptitude questions"));
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitAnswers(@RequestBody Map<String, String> userAnswers) {
        try {
            Map<String, String> results = aptitudeService.checkAnswers(userAnswers);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to submit answers"));
        }
    }
}
