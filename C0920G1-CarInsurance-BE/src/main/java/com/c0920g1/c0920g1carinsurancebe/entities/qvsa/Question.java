package com.c0920g1.c0920g1carinsurancebe.entities.qvsa;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "question")
public class Question  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content_question", nullable = false)
//    @Pattern(regexp = "[a-z A-Z0-9#?!@$%^&*-/,.]{30,}", message = "Vui lòng nhập câu hỏi trên 30 kí tự")
    @Size(min = 30, message = "Vui lòng nhập câu hỏi trên 30 kí tự")
    private String contentQuestion;

    @Column(name = "date_question", nullable = false)
    private String dateQuestion;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerSet;


    public Question() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public void setContentQuestion(String contentQuestion) {
        this.contentQuestion = contentQuestion;
    }

    public String getDateQuestion() {
        return dateQuestion;
    }

    public void setDateQuestion(String dateQuestion) {
        this.dateQuestion = dateQuestion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(List<Answer> answerSet) {
        this.answerSet = answerSet;
    }
}
