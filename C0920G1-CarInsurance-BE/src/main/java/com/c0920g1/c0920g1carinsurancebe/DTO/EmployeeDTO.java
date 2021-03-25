package com.c0920g1.c0920g1carinsurancebe.DTO;

import com.c0920g1.c0920g1carinsurancebe.utils.validate_customer.DateOfBirth;

import javax.validation.constraints.*;

//Tuấn làm DTO lấy thông tin Employee
public class EmployeeDTO {

    @NotBlank(message = "Vui lòng nhập tên.")
    @Pattern(regexp = "^[^\\d\\t`~!@#$%^&*()_\\-+=|\\\\{}\\[\\]:;\"'<>,.?\\/]{1,30}$", message = "Tên phải đúng định dạng từ 1-30 kí tự.")
    @Size(max = 40, message = "Tên không quá 40 kí tự.")
    private String name;

    @NotEmpty(message = "Vui lòng chọn giới tính.")
    private String gender;

    @NotEmpty(message = "Vui lòng nhập ngày sinh.")
    @DateOfBirth(message = "Nhân viên chưa đủ 18 tuổi.")
    private String dateOfBirth;

    @NotEmpty(message = "Vui lòng nhập CMND.")
    @Pattern(regexp = "[0-9]{9}", message = "CMND gồm 9 số.")
    private String idCard;

    @NotBlank(message = "Vui lòng nhập số điện thoại.")
    @Pattern(regexp = "^(0)[35789][0-9]{8}$", message = "Số điện thoại phải đúng định dạng 0xxxxxxxxx.")
    private String phone;

    @NotBlank(message = "Vui lòng nhập email")
    @Pattern(regexp = "^[a-z0-9_]+[a-z0-9]@([a-z0-9]+\\.)[a-z]+(|\\.[a-z]+)$", message = "Email phải đúng định dạng abc@xyz.xyz")
    private String email;

    @NotEmpty(message = "Vui lòng nhập địa chỉ.")
    @Pattern(regexp = "^[^\\d\\t`~!@#$%^&*()_\\-+=|\\\\{}\\[\\]:;\"'<>,.?\\/]{1,15}$", message = "Địa chỉ nhập từ 1-15 kí tự.")
    private String address;

    @NotEmpty(message = "Vui lòng nhập thành phố.")
    @Pattern(regexp = "^[^\\d\\t`~!@#$%^&*()_\\-+=|\\\\{}\\[\\]:;\"'<>,.?\\/]{1,15}$", message = "Thành phố nhập từ 1 đến 15 kí tự.")
    private String city;

    @NotBlank(message = "Ảnh không được để trống.")
    private String img;

    @NotNull(message = "Vui lòng chọn chức vụ.")
    private Long position;

    @NotEmpty(message = "Vui lòng nhập tài khoản.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$", message = "Tài khoản phải có 8 đến 15 kí tự thường bao gồm chữ và số.")
    private String users;

    @NotEmpty(message = "Vui lòng nhập mật khẩu")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$", message = "Mật khẩu phải có 8 đến 15 kí tự thường bao gồm chữ và số.")
    private String newPassword;

    private String confirmPassword;

    public EmployeeDTO(String name, String gender, String dateOfBirth, String idCard, String phone, String email, String address, String city, String img, Long position, String users, String newPassword, String confirmPassword) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.idCard = idCard;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.img = img;
        this.position = position;
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

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
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
