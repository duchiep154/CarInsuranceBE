package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.DTO.AccidentDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Accident;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.StatusAccident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccidentService {
    //khoa
    void addListAccident(String reason, String img, Long idContractDetail);

    List<Accident> listAccident();
    //quoc bao
    Page<Accident> findAccidentWaitingApproval(Long idStatus, String nameSearch, Pageable pageable);

    AccidentDTO findAccidentById(Long id);

    void updateAccidentNotApproval(String notApprovalReason, StatusAccident statusAccident, Long id);

    void updateAccidentWasApproval(String money, StatusAccident statusAccident, Long id);

    void updateAccidentWasRefund( StatusAccident statusAccident, Long id);
}
