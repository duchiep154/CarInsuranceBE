package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.StatusAccident;
import com.c0920g1.c0920g1carinsurancebe.repository.StatusAccidentRepository;
import com.c0920g1.c0920g1carinsurancebe.service.StatusAccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusAccidentServiceImpl implements StatusAccidentService {

    @Autowired
    StatusAccidentRepository statusAccidentRepository;

    @Override
    public StatusAccident findById(Long id) {
        return statusAccidentRepository.findById(id).orElse(null);
    }
}
