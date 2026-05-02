package com.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.entity.Questions;

public interface QuestionRepository extends JpaRepository<Questions, Long> {

    List<Questions> findByQuizId(Long quizId);
}
