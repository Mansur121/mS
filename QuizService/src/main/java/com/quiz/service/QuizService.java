package com.quiz.service;

import java.util.List;

import com.quiz.entity.Quiz;

public interface QuizService {

    Quiz add(Quiz quiz);

    Quiz get(Long id);

    List<Quiz> get();

    Quiz getQuestionsofQuiz(Long quizId);

}
