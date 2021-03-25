package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerClientService {
    // cua trang

    Customer findCustomerByUserId(Long id);

    Users findUsersById(Long id);

    void saveCustomer(Long id, String name, String gender, String dateOfBirth, String idCard, String phone,
                      String email, String address, String city, String img);

    boolean checkPass(Long id, String password);


    boolean checkIDCard(String idCard, Long id);

    void saveUsers(Long id, String password);




}


