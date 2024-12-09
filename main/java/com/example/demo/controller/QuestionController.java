//package com.example.demo.controller;
//
//import com.example.demo.entity.Question;
//import com.example.demo.entity.TestCase;
//import com.example.demo.serviceImpl.QuestionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/coding/questions")
//@CrossOrigin(origins = "http://localhost:4200")  // Allow Angular frontend
//public class QuestionController {
//
//    @Autowired
//    private QuestionService questionService;
//
//    @GetMapping("/random")
//    public List<Question> getRandomQuestions() {
//        return questionService.getRandomQuestions(2);  // Fetch 2 random questions
//    }
//
//    @GetMapping("/{questionId}/test-cases")
//    public List<TestCase> getTestCases(@PathVariable Long questionId) {
//        return questionService.getTestCasesForQuestion(questionId);
//    }
//}
//





package com.example.demo.controller;

import com.example.demo.entity.Question;
import com.example.demo.entity.TestCase;
import com.example.demo.serviceImpl.JDoodleService;
import com.example.demo.serviceImpl.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/coding/questions")
@CrossOrigin(origins = "http://localhost:4200")  // Allow Angular frontend
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private JDoodleService jdoodleService;

    @GetMapping("/random")
    public List<Question> getRandomQuestions() {
        return questionService.getRandomQuestions();  // Fetch 2 random questions
    }

    @GetMapping("/{questionId}/test-cases")
    public List<TestCase> getTestCases(@PathVariable Long questionId) {
        return questionService.getTestCasesForQuestion(questionId);
    }

    @PostMapping("/{questionId}/submit")
    public TestResults submitCode(@PathVariable Long questionId, @RequestBody CodeRequest codeRequest) {
        // Fetch test cases for the given question ID
        List<TestCase> testCases = questionService.getTestCasesForQuestion(questionId);

        // Initialize results
        List<TestCaseResult> testCaseResults = new ArrayList<>();

        // Iterate through each test case and execute the user's code
        for (TestCase testCase : testCases) {

            String compilerResponse = jdoodleService.executeCode(
                    codeRequest.getScript(),
                    codeRequest.getLanguage(),
                    codeRequest.getVersionIndex(),
                    testCase.getInput()
            );

            // Parse the response to extract the actual output
            JSONObject jsonResponse = new JSONObject(compilerResponse);
            String actualOutput = jsonResponse.getString("output");

            // Check if the output matches the expected output
            boolean isPassed = actualOutput.trim().equals(testCase.getExpectedOutput().trim());

            // Store the result
            testCaseResults.add(new TestCaseResult(testCase.getInput(), actualOutput, isPassed));
        }

        return new TestResults(testCaseResults);
    }
}

// CodeRequest class is already defined in JDoodleController, so we can reuse it here.

class TestCaseResult {
    private String input;
    private String output;
    private boolean passed;

    // Constructor
    public TestCaseResult(String input, String output, boolean passed) {
        this.input = input;
        this.output = output;
        this.passed = passed;
    }

    // Getters
    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public boolean isPassed() {
        return passed;
    }
}

class TestResults {
    private List<TestCaseResult> testCaseResults;

    // Constructor
    public TestResults(List<TestCaseResult> testCaseResults) {
        this.testCaseResults = testCaseResults;
    }

    // Getter
    public List<TestCaseResult> getTestCaseResults() {
        return testCaseResults;
    }
}

