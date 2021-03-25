//Khanh
package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.DTO.ContractDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import com.c0920g1.c0920g1carinsurancebe.repository.CarRepository;
import com.c0920g1.c0920g1carinsurancebe.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }


    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    //////////Cre: nguyen bao/////////////
    @Override
    public Car findCarById(Long id) {
        return carRepository.findCarById(id);
    }

    @Override
    public void deleteCar(Long id) {
        this.carRepository.deleteCar(id);
    }

    @Override
    public boolean checkContractOfCar(Long id) {
        List<ContractDTO> contractList = carRepository.getAllContractOfCar(id);
        if (contractList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
/////////End of Cre: nguyen bao//////////
}
//Khanh
