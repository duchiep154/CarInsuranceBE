package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.DTO.AccidentDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Accident;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.StatusAccident;
import com.c0920g1.c0920g1carinsurancebe.repository.AccidentRepository;
import com.c0920g1.c0920g1carinsurancebe.service.AccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccidentServiceImpl implements AccidentService {

    @Autowired
    private AccidentRepository accidentRepository;

    //quoc bao
    @Override
    public Page<Accident> findAccidentWaitingApproval(Long idStatus, String nameSearch, Pageable pageable) {
        return accidentRepository.findAllByIdStatus(idStatus, nameSearch, pageable);
    }

    @Override
    public AccidentDTO findAccidentById(Long id) {
        Accident accident = accidentRepository.findAccidentById(id);
        AccidentDTO accidentDTO = new AccidentDTO(accident.getContractDetail().getContract().getCar().getCustomer().getId().toString(),
                accident.getContractDetail().getContract().getCar().getCustomer().getName(),
                accident.getContractDetail().getContract().getCar().getCustomer().getAddress(),
                accident.getContractDetail().getContract().getCar().getNumberPlate(),
                accident.getContractDetail().getContract().getCar().getCarIdNumber(),
                accident.getContractDetail().getContract().getCar().getManufacturer(),
                accident.getReason(), accident.getImg(), accident.getMoney(), accident.getNotApprovedReason(),
                accident.getContractDetail().getContract().getProduct().getProductPrice(),
                accident.getContractDetail().getContract().getCar().getCustomer().getEmail(),
                accident.getContractDetail().getContract().getId().toString());
        return accidentDTO;
    }

    @Override
    public void updateAccidentNotApproval(String notApprovalReason, StatusAccident statusAccident, Long id) {
        accidentRepository.updateAccidentNotApproval(notApprovalReason, statusAccident, id);
    }

    @Override
    public void updateAccidentWasApproval(String money, StatusAccident statusAccident, Long id) {
        accidentRepository.updateAccidentWasApproval(money, statusAccident, id);
    }

    //cường
    @Override
    public void updateAccidentWasRefund(StatusAccident statusAccident, Long id) {
        accidentRepository.updateAccidentWasRefund(statusAccident, id);
    }


    //khoa
    @Override
    public void addListAccident(String reason, String img, Long idContractDetail) {
        accidentRepository.addListAccident(reason, img, idContractDetail);
    }


    @Override
    public List<Accident> listAccident() {
        return accidentRepository.listAccident();
    }

}
