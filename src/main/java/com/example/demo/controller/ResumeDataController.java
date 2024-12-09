package com.example.demo.controller;
import com.example.demo.serviceImpl.PdfExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "http://localhost:4200")
public class ResumeDataController {
    @Autowired
    private PdfExtractionService pdfExtractionService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            // Call the service to extract text from the PDF file
            String extractedText = pdfExtractionService.extractTextFromPdf(file);

            // Prepare the response as a map
            Map<String, String> response = new HashMap<>();
            response.put("text", extractedText);

            // Return the extracted text in the response
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Handle exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to extract text"));
        }
    }
}
