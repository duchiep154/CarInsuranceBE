package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


//Phúc
public interface QuestionService {
    List<Question> findAllQuestion();
    Page<Question> findAllQuestionAndAnswer(Pageable pageable);
    Question saveQuestion(Question question);
    Question findById(long id);
    void delete(long id);
}