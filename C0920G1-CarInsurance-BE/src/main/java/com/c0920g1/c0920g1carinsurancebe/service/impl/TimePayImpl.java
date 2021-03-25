package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.TimePay;
import com.c0920g1.c0920g1carinsurancebe.repository.TimePayRepository;
import com.c0920g1.c0920g1carinsurancebe.service.TimePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimePayImpl implements TimePayService {
    @Autowired
    TimePayRepository timePayRepository;

    @Override
    public void save(TimePay timePay) {
        timePayRepository.save(timePay);
    }

    @Override
    public List<TimePay> findAll() {
        return timePayRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        timePayRepository.deleteById(id);
    }
}
