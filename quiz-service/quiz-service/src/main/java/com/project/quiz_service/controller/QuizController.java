package com.project.quiz_service.controller;
import com.project.quiz_service.dto.QuestionDto;
import com.project.quiz_service.dto.QuizDto;
import com.project.quiz_service.entity.Response;
import com.project.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());

    }

    @GetMapping("/getQuestions/{id}")
    public ResponseEntity<List<QuestionDto>> getQuestions(@PathVariable Integer id) {
        return quizService.getQuestions(id);
    }

    //
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> response) {

        return quizService.calculateResult(id, response);

    }
}







