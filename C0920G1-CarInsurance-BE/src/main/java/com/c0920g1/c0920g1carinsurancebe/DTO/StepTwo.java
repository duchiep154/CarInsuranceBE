package com.c0920g1.c0920g1carinsurancebe.DTO;

import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import com.c0920g1.c0920g1carinsurancebe.validation.CheckYearManuFacturing;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class StepTwo {
    @NotBlank(message = "không được bỏ trống, vui lòng nhập")
    @Pattern(regexp = "[0-9]{2}[A-Z][0-9][-][0-9]{4,5}", message = "Biển số xe phải theo định dạng 43R3-0046")
    private String numberPlate;

    @NotBlank(message = "không được bỏ trống, vui lòng nhập")
    @Pattern(regexp = "[A-Za-z0-9]{9,12}", message = "Số khung phải từ 9 - 12 ký tự")
    private String carIdNumber;

    @NotBlank(message = "không được bỏ trống, vui lòng nhập")
    @Pattern(regexp = "[A-Z]+", message = "Tên nhà sản xuất phải in hoa")
    private String manufacturer;

    @NotBlank(message = "không được bỏ trống, vui lòng nhập")
    @CheckYearManuFacturing
    private String yearManufacturing;
//    private CarTest car;
    private boolean carCheck;
    private String id;
    private String name;
    private String address;

    public StepTwo() {
    }

    public boolean isCarCheck() {
        return carCheck;
    }

    public void setCarCheck(boolean carCheck) {
        this.carCheck = carCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public CarTest getCar() {
//        return car;
//    }
//
//    public void setCar(CarTest car) {
//        this.car = car;
//    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
