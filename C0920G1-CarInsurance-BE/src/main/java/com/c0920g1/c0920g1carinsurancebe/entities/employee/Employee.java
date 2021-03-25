package com.c0920g1.c0920g1carinsurancebe.entities.employee;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[^\\d\\t`~!@#$%^&*()_\\-+=|\\\\{}\\[\\]:;\"'<>,.?\\/]+$", message = "Tên nhập không đúng")
    @NotBlank(message = "Tên không được để trống.")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
    @NotEmpty(message = "Vui lòng nhập ngày sinh")
    private String dateOfBirth;

    @Column(name = "id_card", nullable = false, unique = true)
    @NotEmpty(message = "Vui lòng nhập CMND")
    @Pattern(regexp = "[0-9]{9}", message = "CMND gồm 9 số")
    private String idCard;

    @Column(name = "phone", nullable = false, unique = true)
    @NotBlank(message = "Số điện thoại không được để trống.")
    @Pattern(regexp = "^(0)[35789][0-9]{8}$")
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email không được để trống.")
    @Pattern(regexp = "^[a-z0-9_]+[a-z0-9]@([a-z0-9]+\\.)[a-z]+(|\\.[a-z]+)$")
    private String email;


    @Column(name = "address")
    @NotBlank(message = "Địa chỉ không được để trống.")
    private String address;

    @Column(name = "city")
    @NotEmpty(message = "Vui lòng nhập thành phố")
    private String city;

    @Column(name = "img", columnDefinition = "LONGTEXT")
    @NotBlank(message = "Ảnh không được để trống.")
    private String img;


    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonBackReference(value = "contact_employee")
    private Set<Contract> contractSet;

    public Employee() {
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Set<Contract> getContractSet() {
        return contractSet;
    }

    public void setContractSet(Set<Contract> contractSet) {
        this.contractSet = contractSet;
    }

}
