package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.product.CarType;
import com.c0920g1.c0920g1carinsurancebe.repository.CarTypeRepository;
import com.c0920g1.c0920g1carinsurancebe.service.CarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarTypeServiceImpl implements CarTypeService {
    @Autowired
    private CarTypeRepository carTypeRepository;

    @Override
    public Iterable<CarType> getAll() {
        return carTypeRepository.findAll();
    }

    @Override
    public Optional<CarType> findById(Long id) {
        return null;
    }

    @Override
    public void save(CarType carType) {
        carTypeRepository.saveAndFlush(carType);
    }

    @Override
    public void update(CarType carType) {
    }

    @Override
    public boolean remove(Long id) {
        Optional<CarType> carTypeTemp = carTypeRepository.findById(id);
        if(carTypeTemp.isPresent()){
            carTypeRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<CarType> getCarTypeList() {
        return carTypeRepository.getAllCarTypeByQuery();
    }
}

// ____ + ____ + ____ + ____ + ____ + ____
//        Optional<CarType> carTypeTemp = carTypeRepository.findById(carType.getId());
//        if(carTypeTemp.isPresent()){
//            carTypeRepository.saveAndFlush(carType);
//            return true;
//        }else {
//            return false;
//        }