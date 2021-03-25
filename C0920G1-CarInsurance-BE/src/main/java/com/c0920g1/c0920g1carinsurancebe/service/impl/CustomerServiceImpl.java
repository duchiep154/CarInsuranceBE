package com.c0920g1.c0920g1carinsurancebe.service.impl;
import com.c0920g1.c0920g1carinsurancebe.DTO.ContractDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.repository.CustomerRepository;
import com.c0920g1.c0920g1carinsurancebe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

//Thế Anh
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    //    Lấy danh sách khách hàng phân trang
    @Override
    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAllCustomers(pageable);
    }

    //    Tìm kiếm khách hàng theo tên và khách hàng
    @Override
    public Page<Customer> findAllCustomersByNameContainingOrCityContaining(Pageable pageable, String inputSearch) {
        return customerRepository.findByNameContainingOrCityContaining(pageable, inputSearch);
    }

    @Override
    public Page<Customer> findByBirthday(String birthdayStart, String birthdayEnd, Pageable pageable) {
        return customerRepository.findByBirthday(birthdayStart,birthdayEnd,pageable);
    }

    @Override
    public Page<Customer> findByDayByContract(String dayByContract, Pageable pageable) {
        return customerRepository.findCustomerByContractStartDate(dayByContract ,pageable);
    }


    //    Thêm mới khác hàng
    @Override
    public void save(Customer customer) {
        customerRepository.createCustomer(standardizeName(customer.getName()),
                customer.getGender(),
                customer.getDateOfBirth(),
                customer.getIdCard(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getCity(),
                customer.getImg(),
                customer.getUsers().getId());
    }

    //    Lấy list khách hàng ko phân trang
    @Override
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    //    Tìm kiếm khách hàng theo id
    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id);
    }

    //Thế Anh
    //////////////////// Cre: Nguyen Bao //////////////////
//    nguyen bao find customer by id
    @Override
    public Customer findCustomerById(Long id) {
        return this.customerRepository.findCustomerById(id);
    }

//    nguyen bao update customer by id
    @Override
    public void saveCustomer(Long id, String name, String gender, String dateOfBirth, String idCard, String phone,
                             String email, String address, String city, String img) {
        name = standardizeName(name);
        this.customerRepository.updateCustomer(id, name, gender, dateOfBirth, idCard, phone, email, address, city, img);
    }

//    nguyen bao delete customer by id
    @Override
    public void deleteCustomer(Long id) {
        this.customerRepository.deleteCustomer(id);
    }

//    nguyen bao kiem tra khach hang co hop dong hay khong
    @Override
    public boolean checkExistContract(Long id) {
        List<ContractDTO> contractList = customerRepository.getAllContract(id);
        if (contractList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

//    nguyen bao viet ham chuan hoa ten khach hang truoc khi luu vao database
    public String standardizeName(String name) {
        name = name.toLowerCase();
        name = name.replaceAll("\\s+", " ");
        name = name.trim();
        String name2 = "";
        String temp = "";
        String[] words = name.split("\\s");
        for (String w : words) {
            temp = w.substring(0, 1).toUpperCase();
            for (int i = 1; i < w.length(); i++) {
                temp += w.charAt(i);
            }
            name2 += temp + " ";
        }
        name = name2.trim();
        return name;
    }
    ////////////////End of Cre: Nguyen Bao ///////////////
}
