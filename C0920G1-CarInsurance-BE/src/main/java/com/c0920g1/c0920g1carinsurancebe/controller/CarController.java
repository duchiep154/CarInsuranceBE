package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.repository.CarRepository;
import com.c0920g1.c0920g1carinsurancebe.service.CarService;
import com.c0920g1.c0920g1carinsurancebe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

//Thế anh
@RestController
@RequestMapping(value = "/api/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CarRepository carRepository;

    @GetMapping(value = "/cars-list", params = {"page", "size", "onSorting", "textSorting"})
    public ResponseEntity<Page<Car>> listCustomer(@RequestParam("page") int page,
                                                       @RequestParam("size") int size,
                                                       @RequestParam("onSorting") boolean onSorting,
                                                       @RequestParam("textSorting") String textSorting) {
        try {
            Page<Car> cars;
            if (textSorting.equals("")) {
                cars = carRepository.findAll(PageRequest.of(page, size));
            } else {
                if (!onSorting) {
                    cars = carRepository.findAll(PageRequest.of(page, size, Sort.by(textSorting).ascending()));

                } else {
                    cars = carRepository.findAll(PageRequest.of(page, size, Sort.by(textSorting).descending()));
                }
            }
            if (cars.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(cars, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    Thế anh lấy list xe
    @GetMapping(value = {"/cars-list"})
    public List<Car> listCar() {
        return carService.findAll();
    }

    //    Ham tạo mới xe /  Thế anh
    @PostMapping(value = "/cars-create")
    public Car createCar(@RequestBody Car car) {
        return carService.save(car);
    }

    //    Thế anh viết controller bắt validate backEnd
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

//    @GetMapping(value = "/{id}")
//    public Car getCarId(@PathVariable(value = "id") Long carID) {
//        return carService.findById(carID);
//    }
    //////////Cre: nguyen bao///////////
//    nguyen bao find car by id
    @GetMapping(value = "/car/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Car car = carService.findCarById(id);
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }
// nguyen bao delete car by id
    @DeleteMapping(value = "/car-delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println(carService.checkContractOfCar(id));
        try {
            if (!carService.checkContractOfCar(id)) {
                carService.deleteCar(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /////////////End of Cre: nguyen bao/////////////
}
