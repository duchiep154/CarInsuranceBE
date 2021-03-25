package com.c0920g1.c0920g1carinsurancebe.DTO;

public interface Bill {
    String getCustomer_name();
    String getCustomer_phone();
    String getCustomer_email();
    String getCar_year_manufacturing();
    String getCar_manufacturing();
    String getCar_bks();
    Long getContract_id();
    String getContract_duration();
    String getContract_start_date();
    String getContract_end_date();
    String getProduct_name();
    String getProduct_type_name();
    String getProduct_price();
    String getEmployee_name();
    Double getTotal_money();
    String getPayment();
    String getPayment_type();
    String getStatus_approval();
    String getStatus_pay();
    Long getContract_detail_id();

}
