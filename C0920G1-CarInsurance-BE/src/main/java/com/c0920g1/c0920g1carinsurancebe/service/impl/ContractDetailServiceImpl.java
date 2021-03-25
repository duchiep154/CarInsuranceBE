package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.DTO.Bill;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;
import com.c0920g1.c0920g1carinsurancebe.repository.ContractDetailRepository;
import com.c0920g1.c0920g1carinsurancebe.service.ContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContractDetailServiceImpl implements ContractDetailService {
    @Autowired
    ContractDetailRepository contractDetailRepository;

    //khoa
    @Override
    public List<ContractDetail> listContractDetail() {
        return contractDetailRepository.listContractDetail();
    }

    @Override
    public ContractDetail findByIdQuery(Long id) {
        return contractDetailRepository.findByIdQuery(id);
    }

    @Override
    public ContractDetail findById(Long id) {
        return contractDetailRepository.findById(id).orElse(null);
    }

    @Override
    public void updateContractPayment(String payment,String payment_type, Long id) {
        this.contractDetailRepository.updateContractDetail(payment,payment_type,id);

    }

    @Override
    public ContractDetail save(ContractDetail contractDetail) {
        return this.contractDetailRepository.save(contractDetail);
    }

}
