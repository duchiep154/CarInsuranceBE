package com.c0920g1.c0920g1carinsurancebe.service.impl;//Khanh


import com.c0920g1.c0920g1carinsurancebe.DTO.*;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import com.c0920g1.c0920g1carinsurancebe.repository.CarRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.ContractRepository;
import com.c0920g1.c0920g1carinsurancebe.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    CarRepository carRepository;
    @Override
    public Page<ContractDTO> findAll(Long idUser, Pageable pageable) {
        return contractRepository.findAll(idUser, pageable);
    }
    @Override
    public Page<ContractDTO> findAllByIdContract(Long idUser, Long idContract, Pageable pageable) {
        return contractRepository.findAllByIdContract(idUser, idContract, pageable);
    }
    @Override
    public Page<ContractDTO> findAllByRangeDate(Long idUser, String startDate, String endDate, Pageable pageable) {
        return contractRepository.findAllByRangeDate(idUser, startDate, endDate, pageable);
    }
    @Override
    public Page<ContractDTO> findAllByStatusPay(Long idUser, String statusPay, Pageable pageable) {
        return contractRepository.findAllByStatusPay(idUser, statusPay, pageable);
    }
    @Override
    public Page<ContractDTO> findAllByIdContractAndStartDate(Long idUser, Long idContract, String startDate, String endDate, Pageable pageable) {
        return contractRepository.findAllByIdContractAndStartDate(idUser, idContract, startDate, endDate, pageable);
    }
    @Override
    public Page<ContractDTO> findAllByIdContractAndStatusPay(Long idUser, Long idContract, String statusPay, Pageable pageable) {
        return contractRepository.findAllByIdContractAndStatusPay(idUser, idContract, statusPay, pageable);
    }
    @Override
    public Page<ContractDTO> findAllByRangeDateAndStatusPay(Long idUser, String startDate, String endDate, String statusPay, Pageable pageable) {
        return contractRepository.findAllByRangeDateAndStatusPay(idUser, startDate, endDate, statusPay, pageable);
    }
    @Override
    public Page<ContractDTO> findAllByIdContractAndRangeDateAndStatusPay(Long idUser, Long idContract, String startDate,
                                                                         String endDate, String statusPay, Pageable pageable) {
        return contractRepository.findAllByIdContractAndRangeDateAndStatusPay(idUser, idContract, startDate, endDate,
                statusPay, pageable);
    }
    @Override
    public  ContractFindByIdDTO findContractByIdToDisplayDetail(Long idContract) {
        return contractRepository.findContractByIdToDisplayDetail(idContract);
    }
    @Override
    public Product checkProduct(String nameProduct) {
        return contractRepository.checkProduct(nameProduct);
    }

    @Override
    public Car checkNumberPlate(String nameNumberPlate) {
        return contractRepository.checkNumberPlate(nameNumberPlate);
    }

    @Override
    public Contract findContractToGetStatusPay(Long idContract) {
        return contractRepository.findContractToGetStatusPay(idContract);
    }

    @Override
    public void deleteById(Long idContract) {
        contractRepository.deleteById(idContract);
    }

    @Override
    public void save(Contract contract) {
        contractRepository.save(contract);
    }
    @Override
    public void saveCar(Car car) {
        carRepository.save(car);
    }
    @Override
    public CusDTO showCus(Long id) {
        return contractRepository.showCus(id);
    }
    @Override
    public CarDTO showCar(Long idCar) {
        return contractRepository.showCar(idCar);
    }
    @Override
    public Product getProduct(String product) {
        return contractRepository.getProduct(product);
    }
    @Override
    public List<Car> showCarOfCus(Long idCus) {
        return contractRepository.showCarOfCus(idCus);
    }
    @Override
    public Employee getEmployee() {
        return contractRepository.getEmployee();
    }
    @Override
    public Car getCar(Long idCar) {
        return contractRepository.getCar(idCar);
    }
    @Override
    public Customer getCustomer(Long idCustomer) {
        return contractRepository.getCustomer(idCustomer);
    }
    @Override
    public List<Product> getListProduct() {
        return contractRepository.getListProduct();
    }
    @Override
    public Car getCarForNumPlate(String numPlate) {
        return contractRepository.getCarForNumPlate(numPlate);
    }
    @Override
    public void createContract(int duration, String endDate, String startDate, String statusApproval, String statusPay,
                               Long carId, Long employeeId, Long productId) {
        contractRepository.createContract(duration, endDate, startDate, statusApproval, statusPay, carId, employeeId, productId);
    }
    @Override
    public Contract findContractByIdToUpdatedApproval(Long id) {
        return contractRepository.findContractByIdToUpdatedApproval(id);
    }


    //hàm update của hưng*******************
    @Override
    public void updateContract(Contract contract) {
        System.out.println(contract);
        contractRepository.updateContract(contract.getDuration(), contract.getStartDate(), contract.getEndDate(),
                contract.getStatusPay(), contract.getStatusApproval(), contract.getProduct(), contract.getEmployee(),
                contract.getCar(), contract.getId());
    }

    @Override
    public void deleteContractById(Long id) {
        contractRepository.deleteById(id);
    }

    @Override
    public void updateContractStatusPay(Contract contract) {
        this.contractRepository.updateContractStatusPay(contract.getStatusPay(),contract.getId());
    }

    @Override
    public Contract findContractByIdToUpdateStatusPay(Long id) {
        return contractRepository.findContractByIdToUpdatedStatusPay(id);
    }

    @Override
    public Contract findById(Long id) {
        return this.contractRepository.findById(id).orElse(null);
    }
    public boolean getAllContract(Long id, String statusPay) {
        Contract getAllContract = contractRepository.getAllContract(id,statusPay);
        if (getAllContract.getStatusPay().equals("1") && getAllContract.getId() == id) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Contract findByIdContract(Long id) {
        return contractRepository.findByIdContract(id);
    }
}
//Khanh
