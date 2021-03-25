package com.c0920g1.c0920g1carinsurancebe.DTO;
import com.c0920g1.c0920g1carinsurancebe.utils.validate_customer.DateOfBirth;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class EditCustomerEmployeeDTO {

    //    nguyen bao validate name
    private Long id;

    @Pattern(regexp = "^[^\\d\\t`~!@#$%^&*()_\\-+=|\\\\{}\\[\\]:;\"'<>,.?\\/]{7,40}$",
            message = "Tên riêng chỉ bao gồm chữ cái, không thể chứa ký tự đặc biệt, chữ số và tối đa 40 ký tự.")
    @NotBlank(message = "Vui lòng điền vào trường Họ và tên.")
    private String name;

    private String gender;

    //    nguyen bao validate date of birth
    @DateOfBirth
    @NotBlank(message = "Vui lòng điền vào trường Ngày sinh.")
    private String dateOfBirth;

    // the anh validate id card
    @NotBlank(message = "Vui lòng điền vào trường này")
    @Pattern(regexp = "[0-9]{9}", message = "Chứng minh nhân dân phải có 9 số")
    private String idCard;

    //    nguyen bao validate phone
    @Pattern(regexp ="^0[35789]\\d{8}$" , message = "Số điện thoại chưa đúng định dạng 0(3/5/7/8/9)xxxxxxxx")
    @NotBlank(message = "Vui lòng điền vào trường này")
    private String phone;

    //    nguyen bao validate email
    @Pattern(regexp ="^(\\w+[a-z0-9]+\\.?)+[a-z0-9]+@([a-z]+\\.)+[a-z]+$",
            message = "Vui lòng nhập đúng định dạng abc@abc.abc")
    @NotBlank(message = "Vui lòng điền vào trường này")
    private String email;

    @NotBlank(message = "Vui lòng điền vào trường này")
    private String address;

    @NotBlank(message = "Vui lòng điền vào trường này")
    private String city;


    private String img;

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
}
