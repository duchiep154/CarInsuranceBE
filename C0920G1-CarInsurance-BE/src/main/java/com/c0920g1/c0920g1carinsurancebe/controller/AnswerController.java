package com.c0920g1.c0920g1carinsurancebe.controller;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Answer;
import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Question;
import com.c0920g1.c0920g1carinsurancebe.security.services.UserDetailsImpl;
import com.c0920g1.c0920g1carinsurancebe.service.AnswerService;
import com.c0920g1.c0920g1carinsurancebe.service.MailService;
import com.c0920g1.c0920g1carinsurancebe.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/qa")
@CrossOrigin(origins = "http://localhost:4200")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private MailService mailService;
    // Nguyễn Quang Hoài Linh --start--
    //Hàm ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    // Hiển thị danh sách Q&A
    @GetMapping("/list")
    public ResponseEntity<List<Question>> getListQuestion() {
        List<Question> listQuestion = questionService.findAllQuestion();
        return new ResponseEntity<>(listQuestion, HttpStatus.OK);
    }
    // Lấy id user
    public Users getIdUser() {
        System.out.println(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        long idUser = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        System.out.println(idUser);
        Users users = new Users();
        users.setId(idUser);
        return users;
    }
    // Tạo câu trả lời
    @PostMapping("/add/{id}")
    public ResponseEntity<Void> createNewAnswer(@RequestBody Answer answer, @PathVariable("id") Long id) {
        try {
            Question question = questionService.findById(id);
            answer.setStatus(false);
            answer.setQuestion(question);
            answer.setUsers(getIdUser());
            answerService.saveAnswer(answer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Lấy id câu hỏi
    @GetMapping("/get/{id}")
    public ResponseEntity<Question> getQuestionId(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.findById(id));
    }
    //lấy id câu trả lời
    @GetMapping("/getA/{id}")
    public ResponseEntity<Answer> getAnswerId(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.findByIdAnswer(id));
    }
    // Chỉnh sửa câu trả lời
    @PutMapping("/edit/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @RequestBody Answer answer) {
        try {
            Answer answer1 = answerService.findByIdAnswer(id);
            if (answer1 == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                answer.setId(answer1.getId());
                answer.setDateAnswer(answer1.getDateAnswer());
                answer.setQuestion(answer1.getQuestion());
                answer.setUsers(answer1.getUsers());
                answer.setStatus(answer1.getStatus());
                if (getIdUser().getId().equals(answer.getUsers().getId()) && answer1.getStatus().equals(false)) {
                    answerService.saveAnswer(answer);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<Answer> checkStatus(@PathVariable Long id,@RequestBody Answer answer,Question question){
        answer = answerService.findByIdAnswer(id);
        question = questionService.findById(answer.getQuestion().getId());
        long idUser = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (question.getUsers().getId() == idUser){
            answer.setStatus(true);
            answerService.saveAnswer(answer);
        } else {
            answer.setStatus(false);
            answerService.saveAnswer(answer);
        }
        return ResponseEntity.ok(answer);
    }
// Hoài Linh --end--
}