package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.Accident;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;
import com.c0920g1.c0920g1carinsurancebe.repository.AccidentRepository;
import com.c0920g1.c0920g1carinsurancebe.service.AccidentService;
import com.c0920g1.c0920g1carinsurancebe.service.ContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccidentController {
    @Autowired
    private AccidentService accidentService;
    @Autowired
    private ContractDetailService contractDetailService;
    @Autowired
    AccidentRepository accidentRepository;

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

    //  list accident(khoa)
    @GetMapping(value = "/api/accident", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Accident> listAllAccident() {
        List<Accident> accidentList = accidentService.listAccident();
        return accidentList;
    }

    // thêm mới yêu cầu đền bù(khoa)

    @PostMapping(value = "/api/accident/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccident(@Valid @RequestBody Accident accident) {
        try {
            accidentService.addListAccident(accident.getReason(), accident.getImg(), accident.getContractDetail().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // test lấy contractDetail theo Id(khoa)
    @GetMapping(value = "/api/contractDetail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContractDetail getContractDetailById(@PathVariable("id") Long id) {
        ContractDetail contractDetail = contractDetailService.findByIdQuery(id);
        return contractDetail;
    }

    // test lấy list contractDetail(khoa)
//    @GetMapping(value = "/api/contractDetail", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<ContractDetail> listAllContractDetail() {
//        List<ContractDetail> contractDetailList = contractDetailService.listContractDetail();
//
//        return contractDetailList;
//    }
//
//    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String pass = bCryptPasswordEncoder.encode("123456789");
//        System.out.println(pass);
//    }
}