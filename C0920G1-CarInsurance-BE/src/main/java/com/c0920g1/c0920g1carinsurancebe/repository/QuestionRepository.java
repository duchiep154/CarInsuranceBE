package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


//Phúc
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value =  "select * from question order by id DESC", nativeQuery = true)
    Page<Question> findAllQuestionAndAnswer(Pageable pageable);


    @Query(value =  "select * from Question order by id DESC ", nativeQuery =  true)
    List<Question> findAllQuestion();


//    @Query(value = "update Question set content_question = ? , date_question = ?, status = ? where id = ?", nativeQuery = true)
//    Question saveQuestionAndAnswer(Question question);
}