//Khanh
package com.c0920g1.c0920g1carinsurancebe.controller.contract;

import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import com.c0920g1.c0920g1carinsurancebe.service.CarService;
import com.c0920g1.c0920g1carinsurancebe.service.EmployeeService;
import com.c0920g1.c0920g1carinsurancebe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/product")
public class ProductControllerTest {
    @Autowired
    ProductService productService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CarService carService;

    @GetMapping(value = "/product")
    public ResponseEntity<List<Product>> showListProduct(Pageable pageable){
        Page<Product> products = productService.getAllProductByQuery(pageable);
        if (products == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products.getContent(), HttpStatus.OK);
    }
    @GetMapping(value = "/employee")
    public ResponseEntity<List<Employee>> showListEmployee(){
        List<Employee> employees = employeeService.finAllEmployee();
        if (employees == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @GetMapping(value = "/car")
    public ResponseEntity<List<Car>> showListCar(){
        List<Car> cars = carService.findAll();
        if (cars == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
}
//Khanh
