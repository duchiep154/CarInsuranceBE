package com.c0920g1.c0920g1carinsurancebe.DTO;

import com.c0920g1.c0920g1carinsurancebe.validation.CheckYearManuFacturing;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContractAndCarDTO implements Validator {
//    private StepOne stepOne;
//    private StepTwo stepTwo;

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

    @NotBlank(message = "không được bỏ trống")
    private String time;


    private String startDate;
    private String endDate;
    private String statusPay;
    private String statusApproval;
    @NotBlank(message = "không được bỏ trống")
    private String product;

    public ContractAndCarDTO() {
    }

//    public StepOne getStepOne() {
//        return stepOne;
//    }
//
//    public void setStepOne(StepOne stepOne) {
//        this.stepOne = stepOne;
//    }

//    public StepTwo getStepTwo() {
//        return stepTwo;
//    }
//
//    public void setStepTwo(StepTwo stepTwo) {
//        this.stepTwo = stepTwo;
//    }


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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatusPay() {
        return statusPay;
    }

    public void setStatusPay(String statusPay) {
        this.statusPay = statusPay;
    }

    public String getStatusApproval() {
        return statusApproval;
    }

    public void setStatusApproval(String statusApproval) {
        this.statusApproval = statusApproval;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ContractAndCarDTO ContractAndCarDTO = (ContractAndCarDTO) target;
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = simpleDateFormat.parse(ContractAndCarDTO.startDate);
            d2 = simpleDateFormat.parse(ContractAndCarDTO.endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(d1.compareTo(d2) >= 0){
            errors.rejectValue("startDate", "startDate.combined");
        }
    }
}
