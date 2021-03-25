package com.c0920g1.c0920g1carinsurancebe.service.Impl;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.repository.CustomerClientRepository;
import com.c0920g1.c0920g1carinsurancebe.service.CustomerClientService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerClientServiceImpl implements CustomerClientService {

    // cua trang

    @Autowired
    private CustomerClientRepository customerClientRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Customer findCustomerByUserId(Long id) {
        return customerClientRepository.findCustomerByUserId(id);
    }

    @Override
    public Users findUsersById(Long id) {
        return customerClientRepository.findUsersById(id);
    }

    @Override
    public void saveCustomer(Long id, String name, String gender, String dateOfBirth, String idCard, String phone,
                             String email, String address, String city, String img) {
        this.customerClientRepository.updateCustomer(id, name, gender, dateOfBirth, idCard, phone, email, address, city, img);
    }

    @Override
    public boolean checkPass(Long id, String password) {
        Users users = customerClientRepository.findUsersById(id);
        return encoder.matches(password, users.getPassword());
    }

    @Override
    public boolean checkIDCard(String idCard, Long id) {

        boolean check = false;

        List<String> listIdCard;
        listIdCard = customerClientRepository.findAllIdCard(id);

        for (String i: listIdCard){
            if (idCard.equals(i)){
                check = false;
                return check;
            } else {
                check = true;
            }

        }
        return check;
    }

    @Override
    public void saveUsers(Long id, String password) {
        this.customerClientRepository.saveUsers(id, password);
    }


}
