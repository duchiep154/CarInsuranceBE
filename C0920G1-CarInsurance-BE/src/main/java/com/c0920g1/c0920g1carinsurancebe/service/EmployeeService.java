package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    //Danh sách nhân viên phân trang, sort
    Page<Employee> findAllEmployees(Pageable pageable);
    //Danh sách nhân viên tìm kiếm theo tên và số điện thoại tương đối
    Page<Employee> findAllEmployeesByNameContainingOrPhoneContaining(Pageable pageable, String inputSearch);
    //Tạo mới nhân viên
    void saveEmployee(Employee employee);

    void update(Employee employee);

    //anh tuấn có thể xóa
    List<Employee> finAllEmployee();

    //    hung code chức năng tìm theo id
    Employee findById(long id);

    //nhan
    Employee getEmployeeByIdAccount(long id);
    boolean checkPass(Long id, String password);
    //hưng code chức năng lưu khi update
    void save(Employee employee);

    //hưng code chức năng delete
    void deleteEmployeeById(Long id);


    //hưng code check contract
    boolean getAllContract(Long id);

}
