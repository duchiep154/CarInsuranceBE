package com.c0920g1.c0920g1carinsurancebe.DTO;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;

import java.util.List;

public class BillContractListDTO {


    private Long idUser;
    private Double total_pay;
    private List<Contract> listContract;
    private List<ContractDetail> contractDetailList;

    public BillContractListDTO(Long idUser, Double total_pay, List<Contract> listContract, List<ContractDetail> contractDetailList) {
        this.idUser = idUser;
        this.total_pay = total_pay;
        this.listContract = listContract;
        this.contractDetailList = contractDetailList;
    }

    public BillContractListDTO(String payment, String payment_type, String status_pay, Long idUser, List<Contract> listContract) {


        this.idUser = idUser;
        this.listContract = listContract;
    }

    public List<ContractDetail> getContractDetailList() {
        return contractDetailList;
    }

    public void setContractDetailList(List<ContractDetail> contractDetailList) {
        this.contractDetailList = contractDetailList;
    }

    public Double getTotal_pay() {
        return total_pay;
    }

    public void setTotal_pay(Double total_pay) {
        this.total_pay = total_pay;
    }

    public List<Contract> getListContract() {
        return listContract;
    }

    public void setListContract(List<Contract> listContract) {
        this.listContract = listContract;
    }



    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }


}


