package com.c0920g1.c0920g1carinsurancebe.entities.account;


import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Answer;
import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Question;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

//Tran Minh Chien
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_name")
        })
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_name")
    @NotBlank(message = "Vui lòng nhập tên tài khoản!")
    private String username;


    @Column(name = "password")
    @NotBlank(message = "Vui lòng nhập mật khẩu.")
    private String password;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonBackReference(value = "user_employee")
    @OneToOne(mappedBy = "users")
    private Employee employee;

    @OneToOne(mappedBy = "users")
    @JsonBackReference(value = "customer")
    private Customer customer;


    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)

    @JsonBackReference(value = "question_user")

    private Set<Question> questionSet;


    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonBackReference(value = "answer_user")
    private Set<Answer> answerSet;



    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public Set<Question> getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(Set<Question> questionSet) {
        this.questionSet = questionSet;
    }

    public Set<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(Set<Answer> answerSet) {
        this.answerSet = answerSet;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}


