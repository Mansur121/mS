package com.question.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.question.entity.Questions;
import com.question.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    public static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Questions> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Questions getOne(@PathVariable("id") Long id) {
        return questionService.getOne(id);
    }

    @PostMapping
    public Questions create(@RequestBody Questions question) {
        return questionService.create(question);
    }

    @GetMapping("/quiz/{quizId}")
    public List<Questions> getQuestionsofQuiz(@PathVariable("quizId") Long quizId) {
        return questionService.getQuestionsOfQuiz(quizId);
    }

}
