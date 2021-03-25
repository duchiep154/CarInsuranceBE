package com.c0920g1.c0920g1carinsurancebe.entities.customer;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.validation.CheckYearManuFacturing;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "không được bỏ trống, vui lòng nhập")
    @Pattern(regexp = "[0-9]{2}[A-Z][0-9][-][0-9]{4,5}", message = "Biển số xe phải theo định dạng 43R3-0046")
    @Column(name = "number_plate", nullable = false)
    private String numberPlate;

    @NotBlank(message = "không được bỏ trống, vui lòng nhập")
    @Pattern(regexp = "[A-Za-z0-9]{9,12}", message = "Số khung phải từ 9 - 12 ký tự")
    @Column(name = "car_id_number", nullable = false)
    private String carIdNumber;

    @NotBlank(message = "không được bỏ trống, vui lòng nhập")
    @Pattern(regexp = "[A-Z]+", message = "Tên nhà sản xuất phải in hoa")
    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @NotBlank(message = "không được bỏ trống, vui lòng nhập")
    @CheckYearManuFacturing
    @Column(name = "year_manufacturing", nullable = false)
    private String yearManufacturing;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @JsonBackReference("car_contact")
    private Set<Contract> contractSet;


    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getCarIdNumber() {
        return carIdNumber;
    }

    public void setCarIdNumber(String carIdNumber) {
        this.carIdNumber = carIdNumber;
    }

    public Set<Contract> getContractSet() {
        return contractSet;
    }

    public void setContractSet(Set<Contract> contractSet) {
        this.contractSet = contractSet;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getYearManufacturing() {
        return yearManufacturing;
    }

    public void setYearManufacturing(String yearManufacturing) {
        this.yearManufacturing = yearManufacturing;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
