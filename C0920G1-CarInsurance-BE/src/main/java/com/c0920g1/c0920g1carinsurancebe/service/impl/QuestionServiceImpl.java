package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Question;
import com.c0920g1.c0920g1carinsurancebe.repository.QuestionRepository;
import com.c0920g1.c0920g1carinsurancebe.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

//Lê Nguyễn Đình Phúc Question
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> findAllQuestion() {
        return questionRepository.findAllQuestion();
    }

    @Override
    public Page<Question> findAllQuestionAndAnswer(Pageable pageable) {
        return questionRepository.findAllQuestionAndAnswer(pageable);
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question findById(long id) {
        return questionRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        questionRepository.deleteById(id);
    }
}
