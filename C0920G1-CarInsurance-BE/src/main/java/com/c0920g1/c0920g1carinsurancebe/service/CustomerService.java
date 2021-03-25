package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
//Thế Anh
@Service
public interface CustomerService {

    Page<Customer> findAllCustomers(Pageable pageable);

    Page<Customer> findAllCustomersByNameContainingOrCityContaining(Pageable pageable, String inputSearch);

    Page<Customer> findByBirthday(String birthdayStart  , String birthdayEnd, Pageable pageable);

    Page<Customer> findByDayByContract(String dayByContract , Pageable pageable);



    void save(Customer customer);

    List<Customer> findAllCustomer();

    Customer findById(long id);
    //////////////////// Cre: Nguyen Bao //////////////////
    Customer findCustomerById(Long id);
    void saveCustomer(Long id, String name, String gender, String dateOfBirth, String idCard, String phone,
                      String email, String address, String city, String img);
    void deleteCustomer(Long id);
//    nguyen bao kiem tra khach hang co hop dong hay khong
    boolean checkExistContract(Long id);
    //////////////////// End of Cre: Nguyen Bao //////////////////

}
