package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.serviceImpl.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "http://localhost:4200")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @PostMapping("/save")
    public ResponseEntity<Result> saveResult(@RequestBody Map<String, String> resultData) {
        String username = resultData.get("username");
        String mcqScore = resultData.get("mcqScore");
        String mcqTotalQuestions = resultData.get("mcqTotalQuestions");
        String aptitudeScore = resultData.get("aptitudeScore");
        String aptitudeQuestions = resultData.get("aptitudeQuestions");
        String codingScore = resultData.get("codingScore");
        String codingTotalQuestions = resultData.get("codingTotalQuestions");

        Result savedResult = resultService.saveResult(
                username,
                mcqScore,
                mcqTotalQuestions,
                aptitudeScore,
                aptitudeQuestions,
                codingScore,
                codingTotalQuestions
        );

        return ResponseEntity.ok(savedResult);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Result>> getUserResults(@RequestParam String username) {
        List<Result> results = resultService.getUserResults(username);
        return ResponseEntity.ok(results);
    }
}
