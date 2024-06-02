package com.project.question_service.controller;

import com.project.question_service.dto.QuestionDto;
import com.project.question_service.entity.Question;

import com.project.question_service.entity.Response;
import com.project.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    Environment environment;

    @GetMapping("/question")
    public ResponseEntity<List<Question>> getQuestion() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/question/{category}")
    public List<Question> getQuestionByCategory(@PathVariable String category) {
        return questionService.getQuestionByCategory(category);
    }


    @PostMapping("/addquestion")
    public  ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> generateQuestion(
            @RequestParam String categoryName, @RequestParam Integer numQuestions) {
        return questionService.generateQuestion(categoryName, numQuestions);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionDto>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        System.out.println(environment.getProperty("localhost.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }


}
