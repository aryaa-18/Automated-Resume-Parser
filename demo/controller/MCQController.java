package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.serviceImpl.PdfExtractionService;
import com.example.demo.serviceImpl.MCQService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mcq")
@CrossOrigin(origins = "http://localhost:4200")
public class MCQController {

    @Autowired
    private PdfExtractionService pdfExtractionService;

    @Autowired
    private MCQService mcqService;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateMCQs(@RequestParam("file") MultipartFile file) {
        try {
            // Extract text from the uploaded resume
            String extractedText = pdfExtractionService.extractTextFromPdf(file);

            // Generate MCQs based on the extracted text
            String mcqsAndAnswers = mcqService.generateMCQs(extractedText);

            // Prepare the response excluding answers
            Map<String, String> response = new HashMap<>();
            response.put("mcqs", mcqsAndAnswers);

            // Call the reset method after task completion
            mcqService.resetGeminiContext();

            // Return the generated MCQs in the response
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to generate MCQs"));
        }
    }

    @GetMapping("/mcqs")
    public ResponseEntity<Map<String, String>> getMCQs() {
        try {
            String mcqs = mcqService.getLastGeneratedMCQs();
            return ResponseEntity.ok(Collections.singletonMap("mcqs", mcqs));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to fetch MCQs"));
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitAnswers(@RequestBody Map<String, String> userAnswers) {
        try {
            // Compare the user's answers with the correct answers
            Map<String, String> results = mcqService.checkAnswers(userAnswers);

            // Return the results
            return ResponseEntity.ok(results);

        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to submit answers"));
        }
    }
}

