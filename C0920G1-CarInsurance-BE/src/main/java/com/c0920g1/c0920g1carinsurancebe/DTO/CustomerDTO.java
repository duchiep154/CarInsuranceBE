package com.c0920g1.c0920g1carinsurancebe.DTO;

import com.c0920g1.c0920g1carinsurancebe.utils.validate_customer.DateOfBirth;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CustomerDTO {
    @Pattern(regexp = "^[^\\d\\t`~!@#$%^&*()_\\-+=|\\\\{}\\[\\]:;\"'<>,.?\\/]+$",
            message = "Tên riêng chỉ bao gồm chữ cái, không thể chứa ký tự đặc biệt và chữ số")
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String name;

    @NotNull(message = "Vui lòng chọn giới tính")
    private String gender;


    @DateOfBirth
    @NotBlank(message = "Vui lòng điền vào trường này")
    @NotEmpty(message = "Vui lòng nhập ngày sinh")
    private String dateOfBirth;

    @NotBlank(message = "Số chứng minh nhân dân không được để trống")
    @Pattern(regexp = "[0-9]{9}", message = "Chứng minh nhân dân phải có 9 số")
    @NotEmpty(message = "Vui lòng nhập CMND")
    @Pattern(regexp = "[0-9]{9}", message = "CMND gồm 9 số")
    private String idCard;


    @Pattern(regexp ="^0[35789]\\d{8}$" , message = "Số điện thoại chưa đúng định dạng (0xxxxxxxxx)")
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @Pattern(regexp ="^(\\w+[a-z0-9]+\\.?)+[a-z0-9]+@([a-z]+\\.?)+[a-z]+\\.com$",
            message = "Vui lòng nhập đúng định dạng abc@abc.com")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotEmpty(message = "Vui lòng nhập địa chỉ")
    private String address;

    @NotEmpty(message = "Vui lòng nhập thành phố")
    private String city;

    @NotBlank(message = "Ảnh không được để trống.")
    private String img;

    @NotBlank(message = "Tài khoản không được để trống")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$" , message = "Tài khoản tối thiểu 8 kí tự gồm cả chữ và số")
    private String users;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$" , message = "Mật khẩu tối thiểu 8 kí tự gồm cả chữ và số")
    private String newPassword;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String confirmPassword;

    public CustomerDTO( String name, String gender, String dateOfBirth, String idCard, String phone,String email, String address, String city, String img, String users, String newPassword, String confirmPassword) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.idCard = idCard;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.img = img;
        this.users = users;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
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

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
