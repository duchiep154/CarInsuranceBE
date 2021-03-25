//Khanh
package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.DTO.*;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query(value = "select contract from Contract contract where contract.id = ?1")
    Contract findByIdContract(Long id);

    //Tuấn làm phía admin
    @Query(value = "select contract from Contract contract")
    Page<Contract> findAllContract(Pageable pageable);

    @Query(value = "SELECT contract FROM Contract contract WHERE contract.endDate LIKE %:inputSearch% OR contract.startDate LIKE %:inputSearch%")
    Page<Contract> findByEndDateContainingOrStartDateContaining(Pageable pageable, @Param("inputSearch") String inputSearch);


    @Query(value = "SELECT contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "contract.statusPay as statusPay, contract.statusApproval as statusApproval, " +
            "(contract.duration * contract.product.productPrice) as totalMoney from Contract contract " +
            "join Car car on contract.car.id = car.id " +
            "join Customer customer on car.customer.id = customer.id " +
            "where customer.users.id = ?1 ")
    Page<ContractDTO> findAll(Long idUser, Pageable pageable);

    @Query(value = "SELECT contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "contract.statusPay as statusPay, contract.statusApproval as statusApproval, " +
            "(contract.duration * contract.product.productPrice) as totalMoney from Contract contract " +
            "join Car car on contract.car.id = car.id " +
            "join Customer customer on car.customer.id = customer.id " +
            "where customer.users.id = ?1 " +
            "and contract.id = ?2")
    Page<ContractDTO> findAllByIdContract(Long idUser, Long idContract, Pageable pageable);

    @Query(value = "SELECT contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "contract.statusPay as statusPay, contract.statusApproval as statusApproval, " +
            "(contract.duration * contract.product.productPrice) as totalMoney from Contract contract " +
            "join Car car on contract.car.id = car.id " +
            "join Customer customer on car.customer.id = customer.id " +
            "where customer.users.id = ?1 " +
            "and contract.startDate between ?2 and ?3")
    Page<ContractDTO> findAllByRangeDate(Long idUser, String startDate, String endDate, Pageable pageable);

    @Query(value = "SELECT contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "contract.statusPay as statusPay, contract.statusApproval as statusApproval, " +
            "(contract.duration * contract.product.productPrice) as totalMoney from Contract contract " +
            "join Car car on contract.car.id = car.id " +
            "join Customer customer on car.customer.id = customer.id " +
            "where customer.users.id = ?1 " +
            "and contract.statusPay like %?2% ")
    Page<ContractDTO> findAllByStatusPay(Long idUser, String statusPay, Pageable pageable);

    @Query(value = "SELECT contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "contract.statusPay as statusPay, contract.statusApproval as statusApproval, " +
            "(contract.duration * contract.product.productPrice) as totalMoney from Contract contract " +
            "join Car car on contract.car.id = car.id " +
            "join Customer customer on car.customer.id = customer.id " +
            "where customer.users.id = ?1 " +
            "and contract.id = ?2 " +
            "and contract.startDate between ?3 and ?4")
    Page<ContractDTO> findAllByIdContractAndStartDate(Long idUser, Long idContract, String startDate,
                                                      String endDate, Pageable pageable);

    @Query(value = "SELECT contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "contract.statusPay as statusPay, contract.statusApproval as statusApproval, " +
            "(contract.duration * contract.product.productPrice) as totalMoney from Contract contract " +
            "join Car car on contract.car.id = car.id " +
            "join Customer customer on car.customer.id = customer.id " +
            "where customer.users.id = ?1 " +
            "and contract.id = ?2 " +
            "and contract.statusPay like %?3% ")
    Page<ContractDTO> findAllByIdContractAndStatusPay(Long idUser, Long idContract, String statusPay, Pageable pageable);

    @Query(value = "SELECT contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "contract.statusPay as statusPay, contract.statusApproval as statusApproval, " +
            "(contract.duration * contract.product.productPrice) as totalMoney from Contract contract " +
            "join Car car on contract.car.id = car.id " +
            "join Customer customer on car.customer.id = customer.id " +
            "where customer.users.id = ?1 " +
            "and contract.startDate between ?2 and ?3 " +
            "and contract.statusPay like %?4% ")
    Page<ContractDTO> findAllByRangeDateAndStatusPay(Long idUser, String startDate, String endDate, String statusPay,
                                                     Pageable pageable);

    @Query(value = "SELECT contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "contract.statusPay as statusPay, contract.statusApproval as statusApproval, " +
            "(contract.duration * contract.product.productPrice) as totalMoney from Contract contract " +
            "join Car car on contract.car.id = car.id " +
            "join Customer customer on car.customer.id = customer.id " +
            "where customer.users.id = ?1 " +
            "and contract.id = ?2 " +
            "and contract.startDate between ?3 and ?4 " +
            "and contract.statusPay like %?5% ")
    Page<ContractDTO> findAllByIdContractAndRangeDateAndStatusPay(Long idUser, Long idContract, String startDate,
                                                                  String endDate, String statusPay, Pageable pageable);

    @Query(value = "select contract.id as id, contract.startDate as startDate, contract.endDate as endDate," +
            "customer.name as name, car.numberPlate as numberPlate, product.name as productName,  " +
            "(contract.duration * contract.product.productPrice) as totalMoney " +
            "from Contract contract " +
            "join Car car on car.id = contract.car.id " +
            "join Customer customer on customer.id = car.customer.id " +
            "join Product product on product.id = contract.product.id " +
            "where contract.id = ?1")
    ContractFindByIdDTO findContractByIdToDisplayDetail(Long id);

    @Query(value = "select product from Product product where product.name = ?1")
    Product checkProduct(String nameProduct);

    @Query(value = "select car from Car car where car.numberPlate = ?1")
    Car checkNumberPlate(String nameNumberPlate);

    @Query(value = "select employee from Employee employee " +
            "where employee.id = 1")
    Employee getEmployee();

    @Query(value = "select car from Car car " +
            "where car.id = ?1")
    Car getCar(Long idCar);

    @Query(value = "select contract from Contract contract where contract.id = ?1")
    Contract findContractToGetStatusPay(Long idContract);

    @Query(value = "select contract from Contract contract")
    List<Contract> findAllToCheckApproval();

    //************** cua hiep dung xoa loi toi hiep nhe*********************
//    @Query(value = "select customer.id as customer_id, " +
//            " customer.name as customer_name, " +
//            " car.car_id_number as car_bks, " +
//            " contract.id as contract_id, " +
//            " contract.duration as contract_duration, " +
//            " contract.start_date as contract_start_date, " +
//            " contract.end_date as contract_end_date, " +
//            " product.name as product_name, " +
//            " product_type.name as product_type_name, " +
//            " product.product_price," +
//            " employee.name as employee_name, " +
//            "sum(product.product_price * contract.uses) as total_money " +
//            "from contract " +
//            "left join car on car.id = contract.car_id  " +
//            "    left join product on product.id = contract.product_id " +
//            "    left join product_type on product_type.id=id_product_type " +
//            "    left join customer  on car.customer_id = customer.id " +
//            "    left join employee on contract.employee_id=employee.id " +
//            "where contract.id=?1 " +
//            "group by contract.id ", nativeQuery = true)
//    Bill findBillByContractId(long id);


    //***************hiep********************
//    Hàm update của hưng nghiêm cấm xóa
    @Modifying
    @Query("update Contract c set c.duration = ?1 ,c.startDate = ?2 , c.endDate = ?3 , c.statusPay = ?4, c.statusApproval = ?5,c.product = ?6 , c.employee = ?7 , c.car = ?8 where c.id = ?9")
    void updateContract(int duration, String startDate, String endDate, String statusPay, String statusApproval, Product product, Employee employee, Car car, Long id);

    //    Hàm của hưng cấm xóa
    @Modifying
    @Query("delete from Contract c WHERE c.id = ?1")
    void deleteById(Long id);

    //    check hợp đồng đã thanh toán hay chưa
    @Query("select c from Contract c where c.id =?1 and c.statusPay = ?2")
    Contract getAllContract(Long id, String statusPay);
//

    @Query(value = "select customer from Customer customer " +
            "where customer.users.id = ?1")
    Customer getCustomer(Long idCustomer);

    @Query(value = "select product from Product product")
    List<Product> getListProduct();

    @Query(value = "select customer.id as id, customer.name as name, customer.address as address from Customer customer " +
            "where customer.users.id = ?1")
    CusDTO showCus(Long id);

    @Query(value = "select car.carIdNumber as carIdNumber, car.numberPlate as numberPlate, " +
            "car.manufacturer as manufacturer, car.yearManufacturing as yearManufacturing from Car car " +
            "where car.id = ?1")
    CarDTO showCar(Long idCar);

    @Query(value = "select product from Product product " +
            "where product.name = ?1")
    Product getProduct(String product);


    @Query(value = "select car from Car car " +
            "where car.numberPlate = ?1")
    Car getCarForNumPlate(String numPlate);

    @Query(value = "select car from Car car " +
            "join Customer customer on customer.id = car.customer.id " +
            "join Users users on customer.users.id = users.id " +
            "where users.id = ?1")
    List<Car> showCarOfCus(Long idCus);

    @Modifying
    @Query(value = "INSERT INTO Contract (duration, end_date, start_date, status_approval, status_pay, car_id, " +
            "employee_id, product_id) " +
            "values (:duration,:endDate,:startDate,:statusApproval,:statusPay, :carId,:employeeId,:productId)",
            nativeQuery = true)
    @Transactional
    void createContract(@Param("duration") int duration, @Param("endDate") String endDate, @Param("startDate") String startDate,
                        @Param("statusApproval") String statusApproval, @Param("statusPay") String statusPay,
                        @Param("carId") Long carId, @Param("employeeId") Long employeeId, @Param("productId") Long productId);

    @Query(value = "select contract from Contract contract " +
            "where contract.id = ?1")
    Contract findContractByIdToUpdatedApproval(Long id);

    //Khanh
//************** cua hiep dung xoa loi toi hiep nhe*********************
    @Query(value = "select contract.id as contract_id, " +
            " customer.name as customer_name, " +
            " customer.phone as customer_phone, " +
            " customer.email as customer_email, " +
            " car.car_id_number as car_bks, " +
            " car.year_manufacturing as car_year_manufacturing, " +
            " car.manufacturer as car_manufacturing, " +
            " contract.duration as contract_duration, " +
            " contract.start_date as contract_start_date, " +
            " contract.end_date as contract_end_date, " +
            " product.name as product_name, " +
            " product_type.name as product_type_name, " +
            " product.product_price," +
            " employee.name as employee_name, " +
            " contract_detail.payment as payment, " +
            " contract_detail.payment_type as payment_type, " +
            " contract.status_approval as status_approval, " +
            " contract.status_pay as status_pay, " +
            " contract_detail.id as contract_detail_id, " +
            "sum(product.product_price * contract.duration) as total_money " +
            "from contract " +
            "left join car on car.id = contract.car_id  " +
            "    left join product on product.id = contract.product_id " +
            "    left join product_type on product_type.id=id_product_type " +
            "    left join customer  on car.customer_id = customer.id " +
            "    left join employee on contract.employee_id=employee.id " +
            "    left join contract_detail on contract.id = contract_detail.contract_id " +

            "where contract.id=?1 " +
            "group by contract.id ", nativeQuery = true)
    Bill findBillByContractId(long id);

    @Query(value = "update Contract c  set c.statusPay =?1  where c.id = ?2")
    void updateContractStatusPay(String status_pay, Long id);

    @Query(value = "select contract from Contract contract " +
            "where contract.id = ?1")
    Contract findContractByIdToUpdatedStatusPay(Long id);


}

