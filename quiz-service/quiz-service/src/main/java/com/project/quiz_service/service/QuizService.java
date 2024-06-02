package com.project.quiz_service.service;
import com.project.quiz_service.dto.QuestionDto;
import com.project.quiz_service.entity.Quiz;
import com.project.quiz_service.entity.Response;
import com.project.quiz_service.feign.QuizFeign;
import com.project.quiz_service.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizDao quizDao;
    @Autowired
   QuizFeign quizFeign;



    public ResponseEntity<String> createQuiz(String category, int numQuestions, String title) {
    List<Integer> questions = quizFeign.generateQuestion(category, numQuestions).getBody();
    Quiz quiz = new Quiz();
    quiz.setTitle(title);
    quiz.setCategory(category);
    quiz.setQuestionsIds(questions);
    quizDao.save(quiz);

    return new ResponseEntity<>("Success", HttpStatus.CREATED);



    }

    public ResponseEntity<List<QuestionDto>> getQuestions(Integer id) {
        Quiz quiz = quizDao.findById(( id)).get();
        List<Integer>questionIds = quiz.getQuestionsIds();
        ResponseEntity<List<QuestionDto>> questionsFromId = quizFeign.getQuestionsFromId(questionIds);
//

        return questionsFromId;
    }
//
    public ResponseEntity<Integer> calculateResult(int id, List<Response> response) {
        ResponseEntity<Integer> score = quizFeign.getScore(response);
        return score;

    }
}
