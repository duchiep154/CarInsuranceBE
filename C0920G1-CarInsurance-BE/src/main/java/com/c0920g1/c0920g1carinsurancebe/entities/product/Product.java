package com.c0920g1.c0920g1carinsurancebe.entities.product;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.utils.Regex;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "product")
public class Product implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    @Pattern(regexp = Regex.NAME_REGEX, message = "Sai định dạng, chỉ bao gồm chữ, số, khoảng trắng, và dấu gạch ngang")
    private String name;

    @Column(name = "preson_number", nullable = false)
    @NotEmpty
    @Pattern(regexp = Regex.NUMBER_PERSON_REGEX, message = "Sai định dạng, chỉ từ 0-60")
    private String personNumber;

    @Column(name = "status_product", nullable = false)
//    @Pattern(regexp = Regex.STATUS_REGEX, message = "Sai định dạng, chỉ bao gồm 2 chuỗi là true hay false")
    private String statusProduct;

    @Column(name = "product_price", nullable = false, columnDefinition = "DOUBLE")
    @Positive(message = "Số nhập vào phải là số nguyên dương lớn hơn 0")
    private Double productPrice;

    @ManyToOne
    @JoinColumn(name = "id_product_for_car_type", referencedColumnName = "id")
    private CarType carType;

    @ManyToOne
    @JoinColumn(name = "id_product_type", referencedColumnName = "id")
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonBackReference(value = "product_contract")
    private Set<Contract> contractSet;

    public Product() {
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPresonNumber(String presonNumber) {
        this.personNumber = personNumber;
    }

    public String getStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(String statusProduct) {
        this.statusProduct = statusProduct;
    }

    public Set<Contract> getContractSet() {
        return contractSet;
    }

    public void setContractSet(Set<Contract> contractSet) {
        this.contractSet = contractSet;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
    }
}


