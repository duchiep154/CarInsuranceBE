
package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.repository.EmployeeRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.UserRepository;
import com.c0920g1.c0920g1carinsurancebe.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Tuấn làm chức năng danh sách nhân viên phân trang, sort
    @Override
    public Page<Employee> findAllEmployees(Pageable pageable) {
        return employeeRepository.findAllEmployees(pageable);
    }
    // Tuấn làm chức năng tìm kiếm nhân viên theo tên và số điện thoại tương đối
    @Override
    public Page<Employee> findAllEmployeesByNameContainingOrPhoneContaining(Pageable pageable, String inputSearch) {
        return employeeRepository.findByNameContainingOrPhoneContaining(pageable, inputSearch);
    }
    // Tuấn làm chức năng tạo mới nhân viên
    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.createEmployee(standardizeName(employee.getName()), employee.getGender(), employee.getDateOfBirth(),
                employee.getIdCard(), employee.getPhone(), employee.getEmail(), standardizeName(employee.getAddress()), standardizeName(employee.getCity()),
                employee.getImg(), employee.getPosition().getId(), employee.getUsers().getId());
    }

    //hưng code chức năng update nhân viên
    @Override
    public void update(Employee employee) {
        employeeRepository.updateEmployee(employee.getName(), employee.getGender(), employee.getDateOfBirth(),
                employee.getIdCard(), employee.getPhone(), employee.getEmail(), employee.getAddress(), employee.getCity(),
                employee.getImg(), employee.getPosition(), employee.getUsers(), employee.getId());
    }

    @Override
    public List<Employee> finAllEmployee() {
        return employeeRepository.findAll();
    }

    //    hung code chức năng tìm theo id
    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id);
    }

    //hưng code chức năng lưu khi update
    @Override
    public void save(Employee employee) {
        employeeRepository.updateEmployee(standardizeName(employee.getName()), employee.getGender(), employee.getDateOfBirth(),
                employee.getIdCard(), employee.getPhone(), employee.getEmail(), employee.getAddress(), employee.getCity(),
                employee.getImg(), employee.getPosition(), employee.getUsers(), employee.getId());
    }
// hàm chuẩn hóa tên của hưng
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

    //hưng code chức năng delete
    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteEmployeeById(id);
    }


    //    hưng code check hợp đồng
    @Override
    public boolean getAllContract(Long id) {
        List<Contract> contractList = employeeRepository.getAllContract(id);
        if (contractList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Employee getEmployeeByIdAccount(long id) {
        return employeeRepository.getEmployeeByIdAccount(id);
    }

    @Override
    public boolean checkPass(Long id, String password) {
        Users users = userRepository.getUserById(id);
        return encoder.matches(password, users.getPassword());
    }
}
