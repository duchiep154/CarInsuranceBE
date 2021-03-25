//Khanh
package com.c0920g1.c0920g1carinsurancebe.controller;
import com.c0920g1.c0920g1carinsurancebe.DTO.*;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.TimePay;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import com.c0920g1.c0920g1carinsurancebe.repository.*;
import com.c0920g1.c0920g1carinsurancebe.service.CarService;
import com.c0920g1.c0920g1carinsurancebe.service.ContractService;
import com.c0920g1.c0920g1carinsurancebe.service.MailService;

import com.c0920g1.c0920g1carinsurancebe.service.ContractService;
import com.c0920g1.c0920g1carinsurancebe.service.MailService;
import com.c0920g1.c0920g1carinsurancebe.service.TimePayService;

import com.c0920g1.c0920g1carinsurancebe.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private CarService carService;
    @Autowired
    MailService mailService;
    @Autowired
    TimePayService timePayService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProductService productService;

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    //Tuấn làm phía admin
    @GetMapping(value = "admin", params = {"size", "inputSearch"})
    public ResponseEntity<Page<Contract>> listContractByStartDateOrEndDate(
            @RequestParam("inputSearch") String inputSearch, @RequestParam("size") int size) {
        try {
            Page<Contract> contracts = contractRepository.findByEndDateContainingOrStartDateContaining(PageRequest.of(0, size), inputSearch);
            if (contracts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "admin/list", params = {"page", "size", "onSorting", "textSorting"})
    public ResponseEntity<Page<Contract>> listContract(@RequestParam("page") int page, @RequestParam("size") int size,
                                                       @RequestParam("onSorting") boolean onSorting, @RequestParam("textSorting") String textSorting) {
        try {
            Page<Contract> contracts;
            if (textSorting.equals("")) {
                contracts = contractRepository.findAllContract(PageRequest.of(page, size));
            } else {
                if (!onSorting) {
                    contracts = contractRepository.findAllContract(PageRequest.of(page, size, Sort.by(textSorting).ascending()));
                } else {
                    contracts = contractRepository.findAllContract(PageRequest.of(page, size, Sort.by(textSorting).descending()));
                }
            }
            if (contracts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createContract(@Valid @RequestBody ContractListDTO contractListDTO) {
        try {
            List<Car> carList = carRepository.findAll();
            if (!carList.isEmpty()) {
                Map<String,String> listError = new HashMap<>();
                for (Car c : carList) {
                    if (c.getCarIdNumber().equals(contractListDTO.getCarIdNumber())) {
                        listError.put("existCarId", "Số khung đã được đăng ký!");
                    }
                }
                if (!listError.isEmpty()){
                    return ResponseEntity
                            .badRequest()
                            .body(listError);
                }
            }
            Car car = new Car();
            car.setNumberPlate(contractListDTO.getNumberPlate());
            car.setCarIdNumber(contractListDTO.getCarIdNumber());
            car.setManufacturer(contractListDTO.getManufacturer());
            car.setYearManufacturing(contractListDTO.getYearManufacturing());
            car.setCustomer(customerRepository.getOne(Long.parseLong(contractListDTO.getCustomer())));
            carRepository.save(car);
            Contract newContract = new Contract();
            newContract.setStartDate(contractListDTO.getStartDate());

            newContract.setEndDate(contractListDTO.getEndDate());
            newContract.setDuration(Integer.parseInt(contractListDTO.getDuration()));
            newContract.setStatusApproval(contractListDTO.getStatusApproval());
            newContract.setStatusPay(contractListDTO.getStatusPay());
            newContract.setEmployee(employeeRepository.getOne(Long.parseLong(contractListDTO.getEmployee())));
            newContract.setProduct(productRepository.getOne(Long.parseLong(contractListDTO.getProduct())));
            newContract.setCar(car);
            contractRepository.save(newContract);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "approval", params = {"idContract", "duration"})
    public ResponseEntity<?> approve(@RequestParam("idContract") Long idContract, @RequestParam("duration") int duration) {
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        LocalDate start = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        calendar.add(Calendar.YEAR,duration);
        Date date2 = calendar.getTime();
        LocalDate end= date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Contract contract = contractService.findContractByIdToUpdatedApproval(idContract);
        contract.setStatusApproval("1");
        contract.setStartDate(""+ start);
        contract.setEndDate(""+ end);
        contractService.save(contract);
        //Khanh
        TimePay timePay = new TimePay();
        timePay.setId(idContract);
        timePay.setCountDown(10);
        timePayService.save(timePay);
        //Khanh
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void sendMail(String mailTo, String content){
        MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
        String acc = content;
        Multipart multipart = null;
        try {
            mimeBodyPart1.setContent(acc,"text/html; charset=utf-8");
            multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart1);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailService.sendMail(mailTo, multipart);
    }
    @GetMapping(value = "/car")
    public ResponseEntity<CarDTO> showCar(@RequestParam Long idCar){
        CarDTO carDTO = contractService.showCar(idCar);
        return new ResponseEntity<>(carDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/carOfCus")
    public ResponseEntity<List<Car>> showCarOfCus(@RequestParam Long idCus){
        System.out.println(idCus);
        List<Car> cars = contractService.showCarOfCus(idCus);
        System.out.println(cars.get(0).getNumberPlate());
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    @GetMapping(value = "/list")
    public ResponseEntity<Page<ContractDTO>> showListContract(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "2") int size,
                                                              @RequestParam Long idUser){
        try{
            Page<ContractDTO> contracts = contractService.findAll(idUser, PageRequest.of(page, size));
            System.out.println(contracts);
            if (contracts == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listByIdContract")
    public ResponseEntity<Page<ContractDTO>> showListContractByIdContract(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "2") int size,
                                                                          @RequestParam Long idUser,
                                                                          @RequestParam Long idContract){

        try {
            Page<ContractDTO> contracts = contractService.findAllByIdContract(idUser, idContract, PageRequest.of(page, size));
            if (contracts == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listByRangeDate")
    public ResponseEntity<Page<ContractDTO>> showListContractByRangeDate(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "2") int size,
                                                                         @RequestParam Long idUser,
                                                                         @RequestParam String startDate,
                                                                         @RequestParam String endDate){

        try{
            Page<ContractDTO> contracts = contractService.findAllByRangeDate(idUser, startDate, endDate,
                    PageRequest.of(page, size));
            if (contracts == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listByStatusPay")
    public ResponseEntity<Page<ContractDTO>> showListContractByStatusPay(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "2") int size,
                                                                         @RequestParam Long idUser,
                                                                         @RequestParam String statusPay){
        try{
            Page<ContractDTO> contracts = contractService.findAllByStatusPay(idUser, statusPay, PageRequest.of(page, size));
            if (contracts == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listByIdContractAndRangeDate")
    public ResponseEntity<Page<ContractDTO>> showListContractByIdContractAndRangeDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam Long idUser,
            @RequestParam Long idContract,
            @RequestParam String startDate,
            @RequestParam String endDate){
        try{
            Page<ContractDTO> contracts = contractService.findAllByIdContractAndStartDate(idUser, idContract, startDate,
                    endDate, PageRequest.of(page, size));
            if (contracts == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listByIdContractAndStatusPay")
    public ResponseEntity<Page<ContractDTO>> showListContractByIdContractAndStatusPay(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam Long idUser,
            @RequestParam Long idContract,
            @RequestParam String statusPay){
        try{
            Page<ContractDTO> contracts = contractService.findAllByIdContractAndStatusPay(idUser, idContract, statusPay,
                    PageRequest.of(page, size));
            if (contracts == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listByRangeDateAndStatusPay")
    public ResponseEntity<Page<ContractDTO>> showListContractByRangeDateAndStatusPay(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam Long idUser,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String statusPay){
        try{
            Page<ContractDTO> contracts = contractService.findAllByRangeDateAndStatusPay(idUser, startDate, endDate,
                    statusPay, PageRequest.of(page, size));
            if (contracts == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listByIdContractAndRangeDateAndStatusPay")
    public ResponseEntity<Page<ContractDTO>> showListContractByIdContractRangeDateAndStatusPay(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam Long idUser,
            @RequestParam Long idContract,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String statusPay){
        try{
            Page<ContractDTO> contracts = contractService.findAllByIdContractAndRangeDateAndStatusPay(idUser, idContract,
                    startDate, endDate, statusPay, PageRequest.of(page, size));
            if (contracts == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contracts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/customer")
    public ResponseEntity<Customer> getCustomer(@RequestParam Long idUser){
        try{
            Customer customer = contractService.getCustomer(idUser);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listProduct")
    public ResponseEntity<List<Product>> getListProduct(){
        try{
            List<Product> products = contractService.getListProduct();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getContract")
    public ResponseEntity<ContractFindByIdDTO> getContractById(@RequestParam Long idContract){
        try{
            ContractFindByIdDTO contract = contractService.findContractByIdToDisplayDetail(idContract);
            return new ResponseEntity<>(contract, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/checkProduct")
    public ResponseEntity<Product> checkProduct(@RequestParam String nameProduct){
        try{
            Product product = contractService.checkProduct(nameProduct);
            if (product == null){
                return new ResponseEntity("không tìm thấy sản phẩm.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/checkNumberPlate")
    public ResponseEntity<Car> checkNumberPlate(@RequestParam String nameNumberPlate){
        try{
            Car car = contractService.checkNumberPlate(nameNumberPlate);
            if (car == null){
                return new ResponseEntity("", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(car, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "/save")
    public ResponseEntity<ContractAndCarDTO> save(@Valid @RequestBody ContractAndCarDTO contractAndCarDTO,
                                                  @RequestParam Long idCar) {
        try {
            Contract contract = new Contract();
            System.out.println(contractAndCarDTO.getTime());
            contract.setDuration(Integer.parseInt(contractAndCarDTO.getTime()));
            contract.setStartDate(contractAndCarDTO.getStartDate());
            contract.setEndDate(contractAndCarDTO.getEndDate());
            contract.setStatusPay(contractAndCarDTO.getStatusPay());
            contract.setStatusApproval(contractAndCarDTO.getStatusApproval());
            contract.setProduct(contractService.getProduct(contractAndCarDTO.getProduct()));
            contract.setEmployee(contractService.getEmployee());
            contract.setCar(contractService.getCar(idCar));
            contractService.save(contract);
            String content = "Quý khách đã tạo mới 1 hợp đồng. Chi tiết xem tại <a href='http://localhost:4200/list-contract'>'http://localhost:4200/list-contract'</a>";
            sendMail("mainamkhanh92@gmail.com", content);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/saveCar")
    public ResponseEntity<ContractAndCarDTO> saveCar(@Valid @RequestBody ContractAndCarDTO contractAndCarDTO,
                                                     @RequestParam Long id){
        try{
            Contract contract = new Contract();
            contract.setDuration(Integer.parseInt(contractAndCarDTO.getTime()));
            contract.setStartDate(contractAndCarDTO.getStartDate());
            contract.setEndDate(contractAndCarDTO.getEndDate());
            contract.setStatusPay(contractAndCarDTO.getStatusPay());
            contract.setStatusApproval(contractAndCarDTO.getStatusApproval());
            contract.setProduct(contractService.getProduct(contractAndCarDTO.getProduct()));
            contract.setEmployee(contractService.getEmployee());
            Car car = new Car();
            car.setCarIdNumber(contractAndCarDTO.getCarIdNumber());
            car.setManufacturer(contractAndCarDTO.getManufacturer());
            car.setNumberPlate(contractAndCarDTO.getNumberPlate());
            car.setYearManufacturing(contractAndCarDTO.getYearManufacturing());
            car.setCustomer(contractService.getCustomer(id));
            contractService.saveCar(car);
            contract.setCar(contractService.getCarForNumPlate(contractAndCarDTO.getNumberPlate()));
            contractService.save(contract);
            String content = "Quý khách đã tạo mới 1 hợp đồng. Chi tiết xem tại <a href='http://localhost:4200/list-contract'>'http://localhost:4200/list-contract'</a>";
            sendMail("mainamkhanh92@gmail.com", content);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //hưng
    @GetMapping("/{id}")
    public ResponseEntity<Contract> getByIdContract(@PathVariable(value = "id") Long id) {
        try {
            Contract contract = contractRepository.findByIdContract(id);
            return ResponseEntity.ok().body(contract);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Hưng******************************************************
    @PutMapping("/updateContract/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable(value = "id") Long id, @RequestBody Contract contract) {

        try {
            Contract contract1 = contractService.findByIdContract(id);
            contract1.setDuration(contract.getDuration());
            contract1.setStartDate(contract.getStartDate());
            contract1.setEndDate(contract.getEndDate());
            contract1.setStatusPay(contract.getStatusPay());
            contract1.setStatusApproval(contract.getStatusApproval());
            contract1.setProduct(contract.getProduct());
            contract1.setEmployee(contract.getEmployee());
            contract1.setCar(contract.getCar());
            contract1.setId(contract.getId());
            contractService.updateContract(contract1);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Contract> deleteContract(@PathVariable long id) {
        try {
            contractService.deleteContractById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/cars")
    public List<Car> getCar() {
        return this.carService.findAll();
    }

    @GetMapping("/employee")
    public List<Employee> getEmployee() {
        return this.employeeService.finAllEmployee();
    }

    @GetMapping("/product")
    public List<Product> getProduct() {
        return this.productService.getAllProduct();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Contract> deleteContract(@PathVariable long id, String statusPay) {
        if (!contractService.getAllContract(id, statusPay)) {
            contractService.deleteContractById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//************************************************

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
}