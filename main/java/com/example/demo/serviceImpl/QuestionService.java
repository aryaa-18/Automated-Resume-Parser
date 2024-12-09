package com.example.demo.serviceImpl;

import com.example.demo.entity.Question;
import com.example.demo.entity.TestCase;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;


    public List<Question> getRandomQuestions() {
        List<Question> allQuestions = questionRepository.findAll();
        Collections.shuffle(allQuestions); // Shuffle the list to get random questions
        return allQuestions.stream().limit(6).collect(Collectors.toList()); // Limit to 6 questions
    }

    public List<TestCase> getTestCasesForQuestion(Long questionId) {
        return testCaseRepository.findByQuestionId(questionId);
    }
}

