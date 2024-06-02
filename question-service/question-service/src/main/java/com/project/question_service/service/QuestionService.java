package com.project.question_service.service;

import com.project.question_service.dto.QuestionDto;
import com.project.question_service.entity.Question;
import com.project.question_service.entity.Response;
import com.project.question_service.repository.QuestionRepo;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionRepo.findAll();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public List<Question> getQuestionByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    public ResponseEntity<Question> addQuestion(Question question) {
        try {
            return new ResponseEntity<>(questionRepo.save(question), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Integer>> generateQuestion(String categoryName, Integer numQuestions) {
        List<Integer> randomQuestionsByCategory = questionRepo.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(randomQuestionsByCategory, HttpStatus.OK);


    }

    public ResponseEntity<List<QuestionDto>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionDto> questionDtos=new ArrayList<>();
        List<Question> questions=new ArrayList<>();
        for(Integer id:questionIds){
            questions.add(questionRepo.findById(id).get());
        }
        for(Question question:questions){
            QuestionDto questionDto=new QuestionDto();
            questionDto.setId(question.getId());
            questionDto.setCategory(question.getCategory());
            questionDto.setDifficultyLevel(question.getDifficultyLevel());
            questionDto.setOption1(question.getOption1());
            questionDto.setOption2(question.getOption2());
            questionDto.setOption3(question.getOption3());
            questionDto.setOption4(question.getOption4());
            questionDto.setQuestion_title(question.getQuestion_title());
            questionDtos.add(questionDto);


        }
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);


    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response r:responses) {
           Question question = questionRepo.findById(r.getId()).get();

            if(r.getResponse().equals(question.getRightanswer())) {
                right++;



            }


        }
        return new ResponseEntity<>(right, HttpStatus.OK);


    }
}
