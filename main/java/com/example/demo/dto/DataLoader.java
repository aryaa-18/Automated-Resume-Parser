package com.example.demo.dto;

import com.example.demo.entity.Question;
import com.example.demo.entity.TestCase;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the questions already exist to prevent duplicates
        if (questionRepository.count() == 0) {
            loadData();
        }
    }

    private void loadData() {
        // Question 1
        Question question1 = new Question();
        question1.setQuestion("Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once.");
        question1.setFunctionSignature("int removeDuplicates(int[] nums)");
        question1.setDifficulty("easy");
        questionRepository.save(question1);
        testCaseRepository.save(new TestCase(question1, "3\n1 1 2", "2"));
        testCaseRepository.save(new TestCase(question1, "10\n0 0 1 1 1 2 2 3 3 4", "5"));

        // Question 2
        Question question2 = new Question();
        question2.setQuestion("You are given an integer array prices where prices[i] is the price of a given stock on the ith day.");
        question2.setFunctionSignature("int maxProfit(int[] prices)");
        question2.setDifficulty("easy");
        questionRepository.save(question2);
        testCaseRepository.save(new TestCase(question2, "6\n7 1 5 3 6 4", "7"));
        testCaseRepository.save(new TestCase(question2, "5\n1 2 3 4 5", "4"));

        // Question 3
        Question question3 = new Question();
        question3.setQuestion("Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.");
        question3.setFunctionSignature("void rotate(int[] nums, int k)");
        question3.setDifficulty("medium");
        questionRepository.save(question3);
        testCaseRepository.save(new TestCase(question3, "7\n1 2 3 4 5 6 7\n3", "5 6 7 1 2 3 4"));
        testCaseRepository.save(new TestCase(question3, "4\n-1 -100 3 99\n2", "3 99 -1 -100"));

        // Question 4
        Question question4 = new Question();
        question4.setQuestion("Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.");
        question4.setFunctionSignature("boolean containsDuplicate(int[] nums)");
        question4.setDifficulty("easy");
        questionRepository.save(question4);
        testCaseRepository.save(new TestCase(question4, "5\n1 2 3 4 5", "false"));
        testCaseRepository.save(new TestCase(question4, "4\n1 2 3 1", "true"));

        // Question 5
        Question question5 = new Question();
        question5.setQuestion("Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.");
        question5.setFunctionSignature("int singleNumber(int[] nums)");
        question5.setDifficulty("easy");
        questionRepository.save(question5);
        testCaseRepository.save(new TestCase(question5, "3\n2 2 1", "1"));
        testCaseRepository.save(new TestCase(question5, "5\n4 1 2 1 2", "4"));

        // Question 6
        Question question6 = new Question();
        question6.setQuestion("You are given a large integer represented as an integer array digits, where each digits[i] is the ith digit of the integer. Increment the large integer by one and return the resulting array.");
        question6.setFunctionSignature("int[] plusOne(int[] digits)");
        question6.setDifficulty("easy");
        questionRepository.save(question6);
        testCaseRepository.save(new TestCase(question6, "3\n1 2 3", "1 2 4"));
        testCaseRepository.save(new TestCase(question6, "1\n9", "1 0"));
    }
}
