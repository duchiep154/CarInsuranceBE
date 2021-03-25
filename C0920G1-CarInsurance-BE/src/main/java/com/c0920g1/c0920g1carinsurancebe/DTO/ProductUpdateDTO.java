package com.c0920g1.c0920g1carinsurancebe.DTO;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.product.CarType;
import com.c0920g1.c0920g1carinsurancebe.entities.product.ProductType;
import com.c0920g1.c0920g1carinsurancebe.utils.Regex;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Set;

public class ProductUpdateDTO {

    private Long id;

    @Pattern(regexp = Regex.NAME_REGEX, message = "Sai định dạng, chỉ bao gồm chữ, số, khoảng trắng, và dấu gạch ngang")
    private String name;

    @Pattern(regexp = Regex.NUMBER_PERSON_REGEX, message = "Sai định dạng, chỉ từ 0-60")
    private String personNumber;

    @Pattern(regexp = Regex.STATUS_REGEX, message = "Sai định dạng, chỉ bao gồm 2 chuỗi là true hay false")
    private String statusProduct;

    @Positive(message = "Số nhập vào phải là số nguyên lớn hơn 0")
    private Double productPrice;

    private CarType carType;

    private ProductType productType;

    private Set<Contract> contractSet;

    public ProductUpdateDTO(){
    }

    public ProductUpdateDTO(
            Long id
            , @Pattern(regexp = Regex.NAME_REGEX, message = "Sai định dạng, chỉ bao gồm chữ, số, khoảng trắng, và dấu gạch ngang") String name
            , @Pattern(regexp = Regex.NUMBER_PERSON_REGEX, message = "Sai định dạng, chỉ từ 0-60") String personNumber
            , @Pattern(regexp = Regex.STATUS_REGEX, message = "Sai định dạng, chỉ bao gồm 2 chuỗi là true hay false") String statusProduct
            , @Positive(message = "Số nhập vào phải là số nguyên lớn hơn 0") Double productPrice
            , CarType carType
            , ProductType productType
            , Set<Contract> contractSet) {
        this.id = id;
        this.name = name;
        this.personNumber = personNumber;
        this.statusProduct = statusProduct;
        this.productPrice = productPrice;
        this.carType = carType;
        this.productType = productType;
        this.contractSet = contractSet;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public String getStatusProduct() {
        return statusProduct;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public CarType getCarType() {
        return carType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Set<Contract> getContractSet() {
        return contractSet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public void setStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setContractSet(Set<Contract> contractSet) {
        this.contractSet = contractSet;
    }
}
