package com.question.service;

import java.util.List;

import com.question.entity.Questions;

public interface QuestionService {

    public List<Questions> getAllQuestions();

    public Questions getOne(Long id);

    public Questions create(Questions question);

    public List<Questions> getQuestionsOfQuiz(Long quizId);

}
