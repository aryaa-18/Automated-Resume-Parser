package com.example.demo.serviceImpl;

import com.example.demo.entity.Result;
import com.example.demo.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;

    public Result saveResult(String username,
                             String mcqScore,
                             String mcqTotalQuestions,
                             String aptitudeScore,
                             String aptitudeQuestions,
                             String codingScore,
                             String codingTotalQuestions) {
        Result result = new Result();
        result.setUsername(username);
        result.setMcqScore(Integer.parseInt(mcqScore));
        result.setMcqTotalQuestions(Integer.parseInt(mcqTotalQuestions));
        result.setAptitudeScore(Integer.parseInt(aptitudeScore));
        result.setAptitudeTotalQuestions(Integer.parseInt(aptitudeQuestions));
        result.setCodingScore(Integer.parseInt(codingScore));
        result.setCodingTotalQuestions(Integer.parseInt(codingTotalQuestions));

        return resultRepository.save(result);
    }

    public List<Result> getUserResults(String username) {
        return resultRepository.findByUsername(username);
    }
}
