package com.c0920g1.c0920g1carinsurancebe.DTO;

public class AccidentDTO {

    private String customerId;
    private String customerName;
    private String customerAddress;
    private String numberPlate;
    private String carIdNumber;
    private String manufacturer;
    private String accidentReason;
    private String accidentImg;
    private String accidentMoney;
    private String notApprovalReason;
    private Double productPrice;
    private String customerMail;
    private String contractId;

    public AccidentDTO(String customerId, String customerName, String customerAddress, String numberPlate,
                       String carIdNumber, String manufacturer, String accidentReason, String accidentImg,
                       String accidentMoney, String notApprovalReason, Double productPrice, String customerMail,String contractId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.numberPlate = numberPlate;
        this.carIdNumber = carIdNumber;
        this.manufacturer = manufacturer;
        this.accidentReason = accidentReason;
        this.accidentImg = accidentImg;
        this.accidentMoney = accidentMoney;
        this.notApprovalReason = notApprovalReason;
        this.productPrice = productPrice;
        this.customerMail = customerMail;
        this.contractId=contractId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
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

    public String getAccidentReason() {
        return accidentReason;
    }

    public void setAccidentReason(String accidentReason) {
        this.accidentReason = accidentReason;
    }

    public String getAccidentImg() {
        return accidentImg;
    }

    public void setAccidentImg(String accidentImg) {
        this.accidentImg = accidentImg;
    }

    public String getAccidentMoney() {
        return accidentMoney;
    }

    public void setAccidentMoney(String accidentMoney) {
        this.accidentMoney = accidentMoney;
    }

    public String getNotApprovalReason() {
        return notApprovalReason;
    }

    public void setNotApprovalReason(String notApprovalReason) {
        this.notApprovalReason = notApprovalReason;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
