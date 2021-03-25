package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.DTO.Bill;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;

import java.util.List;

public interface ContractDetailService {
    //khoa
    List<ContractDetail> listContractDetail();

    ContractDetail findByIdQuery(Long id);

    //hiep

    ContractDetail findById(Long id);

    void updateContractPayment(String payment,String payment_type,Long id);
    ContractDetail save(ContractDetail contractDetail);

}
