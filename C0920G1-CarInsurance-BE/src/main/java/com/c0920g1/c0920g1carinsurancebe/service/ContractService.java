//Khanh
package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.DTO.CarDTO;
import com.c0920g1.c0920g1carinsurancebe.DTO.ContractDTO;
import com.c0920g1.c0920g1carinsurancebe.DTO.ContractFindByIdDTO;
import com.c0920g1.c0920g1carinsurancebe.DTO.CusDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ContractService {
    Contract findContractByIdToUpdatedApproval(Long id);

    Page<ContractDTO> findAll(Long idUser, Pageable pageable);

    Page<ContractDTO> findAllByIdContract(Long idUser, Long idContract, Pageable pageable);

    Page<ContractDTO> findAllByRangeDate(Long idUser, String startDate, String endDate, Pageable pageable);

    Page<ContractDTO> findAllByStatusPay(Long idUser, String statusPay, Pageable pageable);

    Page<ContractDTO> findAllByIdContractAndStartDate(Long idUser, Long idContract, String startDate,
                                                      String endDate, Pageable pageable);

    Page<ContractDTO> findAllByIdContractAndStatusPay(Long idUser, Long idContract, String statusPay, Pageable pageable);

    Page<ContractDTO> findAllByRangeDateAndStatusPay(Long idUser, String startDate, String endDate, String statusPay,
                                                     Pageable pageable);

    Page<ContractDTO> findAllByIdContractAndRangeDateAndStatusPay(Long idUser, Long idContract, String startDate,
                                                                  String endDate, String statusPay, Pageable pageable);

    Customer getCustomer(Long idCustomer);

    List<Product> getListProduct();

    ContractFindByIdDTO findContractByIdToDisplayDetail(Long idContract);

    Product checkProduct(String nameProduct);

    Car checkNumberPlate(String nameNumberPlate);

    Contract findContractToGetStatusPay(Long idContract);

    void deleteById(Long idContract);

    Product getProduct(String product);

    void save(Contract contract);

    void saveCar(Car car);

    CusDTO showCus(Long id);

    CarDTO showCar(Long idCar);

    List<Car> showCarOfCus(Long idCus);

    Employee getEmployee();

    Car getCar(Long idCar);

    Car getCarForNumPlate(String numPlate);

    void createContract(int duration, String endDate, String startDate, String statusApproval, String statusPay,
                        Long carId, Long employeeId, Long productId);

    //    hưng update
    void updateContract(Contract contract);

    //hưng delete
    void deleteContractById(Long id);

    //    check delete hưng
    boolean getAllContract(Long id, String statusPay);

    void updateContractStatusPay(Contract contract);

    Contract findContractByIdToUpdateStatusPay(Long id);

    Contract findById(Long id);


    Contract findByIdContract(Long id);
}
//Kh