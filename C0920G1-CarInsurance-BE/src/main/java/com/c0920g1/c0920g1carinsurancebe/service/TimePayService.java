package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.TimePay;

import java.util.List;

public interface TimePayService {
    void save(TimePay timePay);
    List<TimePay> findAll();
    void deleteById(Long id);
}
