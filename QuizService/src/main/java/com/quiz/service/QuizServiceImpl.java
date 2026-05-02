package com.quiz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quiz.entity.Quiz;
import com.quiz.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

    private QuizRepository quizRepository;
    private QuestionClient questionClient;

    public QuizServiceImpl(QuizRepository quizRepository, QuestionClient questionClient) {
        this.quizRepository = quizRepository;
        this.questionClient = questionClient;
    }

    @Override
    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz get(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @Override
    public List<Quiz> get() {
        List<Quiz> quizez = quizRepository.findAll();

        List<Quiz> newQuizList = quizez.stream().map(quiz -> {
            quiz.setQuestions(questionClient.getQuestionsofQuiz(quiz.getQuizId()));
            return quiz;
        }).collect(Collectors.toList());
        return newQuizList;
    }

    public Quiz getQuestionsofQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setQuestions(questionClient.getQuestionsofQuiz(quizId));
        return quiz;
    }
}
