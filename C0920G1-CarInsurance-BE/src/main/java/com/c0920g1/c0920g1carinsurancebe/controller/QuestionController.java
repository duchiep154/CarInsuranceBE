package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Question;
import com.c0920g1.c0920g1carinsurancebe.security.services.UserDetailsImpl;
import com.c0920g1.c0920g1carinsurancebe.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

//Phúc
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    //Phúc
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

    //Phúc
    //Hàm lấy id user
    public Users getIdUser(){
        System.out.println(((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getId());
        long idUser = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Users users = null;
        users = new Users();
        users.setId(idUser);
        return users;
    }

    //Lê Nguyễn Đình Phúc
    //Hàm lấy danh sách câu hỏi và câu trả lời phân trang
    @GetMapping(value = "/questions", params = {"page", "size"} )
    public ResponseEntity<Page<Question>> getAllQuestionAndAnswer(@RequestParam("page") int page,
                          @RequestParam("size") int size){
        try {
            Page<Question> questions;
                questions = questionService.findAllQuestionAndAnswer(PageRequest.of(page, size));
                return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Lê Nguyễn Đình Phúc
    //Hàm tạo câu hỏi
    @PostMapping("/questions")
    public  ResponseEntity<?> createQuestion(@Valid @RequestBody Question question){
        try {
            question.setUsers(getIdUser());
            questionService.saveQuestion(question);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Lê Nguyễn Đình Phúc
    //Hàm lấy chỉnh sửa câu hỏi
    @PutMapping("/questions/{id}")
    public ResponseEntity<?> editQuestion(@Valid @RequestBody Question question){
        try {
            question.setUsers(getIdUser());
            questionService.saveQuestion(question);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Lê Nguyễn Đình Phúc
    //Hàm lấy câu hỏi theo id
    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable(value = "id") Long questionId){
        Question question = questionService.findById(questionId);
        return ResponseEntity.ok().body(question);
    }

    //Lê Nguyễn Đình Phúc
    //Hàm xóa câu hỏi
    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable(value = "id") long questionId){
        questionService.delete(questionId);
    }

}
