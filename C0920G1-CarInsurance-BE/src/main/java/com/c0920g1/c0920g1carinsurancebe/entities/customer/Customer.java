package com.c0920g1.c0920g1carinsurancebe.entities.customer;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    nguyen bao validate name

    @Pattern(regexp = "^[^\\d\\t`~!@#$%^&*()_\\-+=|\\\\{}\\[\\]:;\"'<>,.?\\/]+$",
            message = "Tên riêng chỉ bao gồm chữ cái, không thể chứa ký tự đặc biệt và chữ số.")
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Tên khách hàng không được để trống.")
    private String name;

    @Column(name = "gender")
    private String gender;

//    nguyen bao validate date of birth
    @Column(name = "date_of_birth", columnDefinition = "DATE")
    private String dateOfBirth;

// the anh validate id card

    @NotBlank(message = "Số chứng minh nhân dân không được để trống.")
    @Pattern(regexp = "[0-9]{9}", message = "Chứng minh nhân dân phải có 9 số.")
    @Column(name = "id_card", nullable = false, unique = true)
    private String idCard;

//    nguyen bao validate phone
    @Pattern(regexp ="^0[35789]\\d{8}$" , message = "Số điện thoại chưa đúng định dạng (0xxxxxxxxx).")
    @NotBlank(message = "Số điện thoại không được để trống.")
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

//    nguyen bao validate email
    @Pattern(regexp ="^(\\w+[a-z0-9]+\\.?)+[a-z0-9]+@([a-z]+\\.?)+[a-z]+$",
            message = "Vui lòng nhập đúng định dạng xxxxx@xxx.com")
    @NotBlank(message = "Email không được để trống")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Địa chỉ không được để trống.")
    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "img", columnDefinition = "LONGTEXT")
    private String img;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonBackReference("customer_car")
    private Set<Car> carSet;

    public Customer() {
    }


    public Customer(String name, String email, String phone, String idCard, String address, Users users) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.idCard = idCard;
        this.address = address;
        this.users = users;
    }

    public Customer(Long id,String name,String dateOfBirth, String gender, String email, String phone, String idCard, String address,String city, String img) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.idCard = idCard;
        this.address = address;
        this.city = city;
        this.img = img;
    }

    public Users getUsers() {
        return users;
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

    public Set<Car> getCarSet() {
        return carSet;
    }

    public void setCarSet(Set<Car> carSet) {
        this.carSet = carSet;
    }



    public Customer(String name,String gender,String dateOfBirth, String idCard, String phone,String email, String address, String city , String img, Users users) {
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

    }

}
