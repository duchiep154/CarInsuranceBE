package com.c0920g1.c0920g1carinsurancebe.entities.contract;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "accident")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "request_day")
    private String requestDay;

    @Column(name = "reason")
    @NotBlank(message = "Không được để trống")
    private String reason;

    @Column(name = "img",columnDefinition = "LONGTEXT")
    @NotBlank(message = "Vui lòng chọn ảnh")
    private String img;

    @Column(name = "not_approved_reason")
    private String notApprovedReason;

    @Column(name = "money")
    private String money;

    @ManyToOne
    @JoinColumn(name = "id_contract_detail" , referencedColumnName = "id")
    private ContractDetail contractDetail;

    @ManyToOne
    @JoinColumn(name = "id_status_accident", referencedColumnName = "id")
    private StatusAccident statusAccident;

    public Accident() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestDay() {
        return requestDay;
    }

    public void setRequestDay(String requestDay) {
        this.requestDay = requestDay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public String getNotApprovedReason() {
        return notApprovedReason;
    }

    public void setNotApprovedReason(String notApprovedReason) {
        this.notApprovedReason = notApprovedReason;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ContractDetail getContractDetail() {
        return contractDetail;
    }

    public void setContractDetail(ContractDetail contractDetail) {
        this.contractDetail = contractDetail;
    }

    public StatusAccident getStatusAccident() {
        return statusAccident;
    }

    public void setStatusAccident(StatusAccident statusAccident) {
        this.statusAccident = statusAccident;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
