package com.c0920g1.c0920g1carinsurancebe.DTO;

import com.c0920g1.c0920g1carinsurancebe.validation.CheckYearManuFacturing;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class ContractListDTO {

    private Long id;

    @NotBlank(message = "Vui lòng chọn kỳ hạn.")
    private String duration;

    @NotEmpty(message = "Vui lòng nhập ngày bắt đầu hợp đồng.")
    private String startDate;

    @NotEmpty(message = "Vui lòng nhập ngày kết thúc hợp đồng.")
    private String endDate;

    @NotBlank(message = "Vui lòng chọn tình trạng thanh toán.")
    private String statusPay;

    @NotBlank(message = "Vui lòng chọn tình trạng phê duyệt.")
    private String statusApproval;

    @NotBlank(message = "Vui lòng chọn bảo hiểm.")
    private String product;

    @NotBlank(message = "Vui lòng chọn nhân viên.")
    private String employee;

    @NotBlank(message = "Vui lòng nhập biển số xe.")
    @Pattern(regexp = "[0-9]{2}[A-Z][0-9][-][0-9]{4,5}", message = "Biển số xe phải theo định dạng 43R3-0046")
    private String numberPlate;

    @NotBlank(message = "Vui lòng nhập số khung.")
    @Pattern(regexp = "[A-Za-z0-9]{9,12}", message = "Số khung phải từ 9 - 12 ký tự.")
    private String carIdNumber;

    @NotBlank(message = "Vui lòng nhập nhà sản xuất.")
    @Pattern(regexp = "[A-Z]+", message = "Tên nhà sản xuất phải in hoa")
    private String manufacturer;

    @NotBlank(message = "Vui lòng nhập năm sản xuất.")
    @CheckYearManuFacturing
    private String yearManufacturing;

    @NotBlank(message = "Vui lòng chọn khách hàng.")
    private String customer;

    public ContractListDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
