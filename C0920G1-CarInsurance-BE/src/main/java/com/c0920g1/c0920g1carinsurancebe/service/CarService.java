//Khanh
package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.DTO.ContractDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
public interface CarService {

    List<Car> findAll();
    Car findById(Long id);
    Car save(Car car);
///////Cre: nguyen bao//////
    Car findCarById(Long id);
    void deleteCar(Long id);
    boolean checkContractOfCar(Long id);

///////End of Cre: nguyen bao////////
}
//Khanh
