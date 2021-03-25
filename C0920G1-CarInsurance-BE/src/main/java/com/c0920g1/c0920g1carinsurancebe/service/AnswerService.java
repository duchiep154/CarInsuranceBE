package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> findAllAnswer();

    Answer saveAnswer(Answer answer);

    Answer findByIdAnswer(Long id);

//    void removeAnswer(Long id);

}
