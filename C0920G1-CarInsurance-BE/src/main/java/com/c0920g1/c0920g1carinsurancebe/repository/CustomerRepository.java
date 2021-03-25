package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.DTO.ContractDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

//Thế Anh
@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    Thế Anh viết create và list

    @Query("SELECT c FROM Customer c")
    Page<Customer> findAllCustomers(Pageable pageable);

    //    Query tìm kiếm theo tên và thành phố
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:inputSearch% OR c.city LIKE %:inputSearch%")
    Page<Customer> findByNameContainingOrCityContaining(Pageable pageable, @Param("inputSearch") String inputSearch);

    //    Query Thêm mới khách hàng
    @Modifying
    @Query(value = "INSERT INTO Customer (name," +
            " gender," +
            " date_of_birth," +
            " id_card, phone," +
            " email, address," +
            " city, img," +
            " user_id) " +
            "values " +
            "(:name," +
            ":gender," +
            ":dayOfBirth," +
            ":idCard," +
            ":phone," +
            " :email," +
            " :address," +
            " :city," +
            " :img," +
            " :userId)",
            nativeQuery = true)
    @Transactional
    void createCustomer(@Param("name") String name,
                        @Param("gender") String gender,
                        @Param("dayOfBirth") String dayOfBirth,
                        @Param("idCard") String idCard,
                        @Param("phone") String phone,
                        @Param("email") String email,
                        @Param("address") String address,
                        @Param("city") String city,
                        @Param("img") String img,
                        @Param("userId") Long userId);

    //    Query tìm kiếm theo id khách hàng
    @Query(value = "select c from Customer c where c.id = ?1")
    Customer findById(long id);

    @Query(value = "SELECT c FROM Customer c WHERE (c.dateOfBirth BETWEEN ?1  AND ?2)")
    Page<Customer> findByBirthday(String birthdayStart, String birthdayEnd, Pageable pageable);

    @Query(value = "SELECT customer FROM Customer customer " +
            " join Car car on car.customer.id = customer.id " +
            " join Contract contract on contract.car.id = car.id " +
            " where contract.startDate = ?1")
    Page<Customer> findCustomerByContractStartDate(String dayByContract, Pageable pageable);


    Page<Customer> findAllByNameContaining(Pageable pageable, String name);



    ////////////Cre: Nguyen Bao///////////////////
// nguyen bao query tìm kiếm theo id khách hàng (cách 1: native query)
    @Query(value = "select * from customer " +
            "where id = ?1",
            nativeQuery = true)
    Customer findCustomerById(Long id);

// nguyen bao query cập nhật khách hàng theo id (cách 2: hibernate query)
    @Modifying
    @Query(value = "update Customer c" +
            " set c.name = ?2, " +
            "c.gender = ?3," +
            " c.dateOfBirth = ?4," +
            " c.idCard = ?5," +
            " c.phone = ?6," +
            " c.email = ?7," +
            " c.address = ?8, " +
            " c.city = ?9," +
            " c.img = ?10 " +
            "where c.id = ?1")
    void updateCustomer(Long id, String name, String gender, String dateOfBirth, String idCard, String phone,
                        String email, String address, String city, String img);

    // nguyen bao query xóa khách hàng theo id
    @Modifying
    @Query(value = "delete from customer " +
            "where id = ?1",
            nativeQuery = true)
    void deleteCustomer(Long id);

    // nguyen bao query lấy danh sách hợp đồng theo id khách hàng
    @Query(value = "select contract.id as id from contract " +
            "join car on car.id = contract.car_id " +
            "join customer on customer.id = car.customer_id " +
            "where customer.id = ?1", nativeQuery = true)
    List<ContractDTO> getAllContract(Long id);
    ////////////End of Cre: Nguyen Bao///////////////////

    Boolean existsCustomerByEmail(String email);

    Boolean existsCustomerByIdCard(String idcard);

}
