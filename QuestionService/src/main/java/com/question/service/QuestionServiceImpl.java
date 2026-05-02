package com.question.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.question.entity.Questions;
import com.question.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Questions> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Questions getOne(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Override
    public Questions create(Questions question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Questions> getQuestionsOfQuiz(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

}
