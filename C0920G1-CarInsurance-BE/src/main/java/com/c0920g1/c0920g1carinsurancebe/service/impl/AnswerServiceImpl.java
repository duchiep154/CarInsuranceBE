package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Answer;
import com.c0920g1.c0920g1carinsurancebe.repository.AnswerRepository;
import com.c0920g1.c0920g1carinsurancebe.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
//Linh
@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public List<Answer> findAllAnswer() {
        return answerRepository.findAll();
    }

    @Override
    public Answer saveAnswer(Answer answer) {
        answer.setDateAnswer(String.valueOf(LocalDateTime.now()));
        return answerRepository.save(answer);
    }

    @Override
    public Answer findByIdAnswer(Long id) {
        return answerRepository.findById(id).orElse(null);
    }
}
