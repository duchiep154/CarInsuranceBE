package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.employee.Position;
import com.c0920g1.c0920g1carinsurancebe.repository.PositionRepository;
import com.c0920g1.c0920g1carinsurancebe.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionRepository positionRepository;
    @Override
    public List<Position> getAllPosition() {
        return positionRepository.findAll();
    }
}
