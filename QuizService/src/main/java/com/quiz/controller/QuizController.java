package com.quiz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.entity.Quiz;
import com.quiz.service.QuizService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public Quiz create(@RequestBody Quiz quiz) {
        return quizService.add(quiz);
    }

    @GetMapping
    public List<Quiz> getAll() {
        return quizService.get();
    }

    @GetMapping("/{id}")
    public Quiz get(@PathVariable("id") Long id) {
        return quizService.get(id);
    }

    @GetMapping("/getQuestion/{quizId}")
    public Quiz getQuestionofQuiz(@PathVariable("quizId") Long quizId) {
        return quizService.getQuestionsofQuiz(quizId);
    }
}
