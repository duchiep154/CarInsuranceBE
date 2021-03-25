package com.c0920g1.c0920g1carinsurancebe.DTO;

public class PayDTO {

  private Long id_contract;
  private Long id_contract_detail;
  private String payment;
  private String payment_type;
  private String status_pay;


    public PayDTO(Long id_contract, Long id_contract_detail, String payment, String payment_type, String status_pay) {
        this.id_contract = id_contract;
        this.id_contract_detail = id_contract_detail;
        this.payment = payment;
        this.payment_type = payment_type;
        this.status_pay = status_pay;
    }

    public Long getId_contract() {
        return id_contract;
    }

    public void setId_contract(Long id_contract) {
        this.id_contract = id_contract;
    }

    public Long getId_contract_detail() {
        return id_contract_detail;
    }

    public void setId_contract_detail(Long id_contrac_detail) {
        this.id_contract_detail = id_contrac_detail;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getStatus_pay() {
        return status_pay;
    }

    public void setStatus_pay(String status_pay) {
        this.status_pay = status_pay;
    }
}
