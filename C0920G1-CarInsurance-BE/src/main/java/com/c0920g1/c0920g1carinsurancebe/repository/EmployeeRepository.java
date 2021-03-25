package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//    Tuấn làm list, create , search

    //Query danh sách nhân viên có phân trang và sort
    @Query("SELECT e FROM Employee e")
    Page<Employee> findAllEmployees(Pageable pageable);
    //Query tìm kiếm nhân viên theo tên và số điện thoại tương đối
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:inputSearch% OR e.phone LIKE %:inputSearch%")
    Page<Employee> findByNameContainingOrPhoneContaining(Pageable pageable, @Param("inputSearch") String inputSearch);
    //Query thêm mới nhân viên
    @Modifying
    @Query(value = "INSERT INTO Employee (name, gender, date_of_birth, id_card, phone, email, address, city, img, position_id, user_id) " +
            "values (:name,:gender,:dayOfBirth,:idCard,:phone, :email,:address,:city,:img,:positionId,:userId)",
            nativeQuery = true)
    @Transactional
    void createEmployee(@Param("name") String name, @Param("gender") String gender, @Param("dayOfBirth") String dayOfBirth,
                        @Param("idCard") String idCard, @Param("phone") String phone,
                        @Param("email") String email, @Param("address") String address, @Param("city") String city,
                        @Param("img") String img, @Param("positionId") Long positionId, @Param("userId") Long userId);

    //    Hưng làm phần edit và delete
    @Modifying
    @Query(value = "update Employee e  set e.name =?1 , e.gender =?2 , e.dateOfBirth =?3 , e.idCard = ?4 , e.phone = ?5 , e.email = ?6 , e.address = ?7 , e.city = ?8 , e.img = ?9 , e.position = ?10 , e.users = ?11 where e.id = ?12")
    void updateEmployee(String name, String gender, String dateOfBirth, String idCard, String phone, String email, String address, String city, String img, Position position, Users users, Long id);

    //    String name , String gender , String dateOfBirth , String idCard , String phone , String email , String address, String city , String img, Position position , Users users , Long id
    @Query(value = "select * from employee", nativeQuery = true)
    List<Employee> findAll();
//lấy dữ liệu theo id của hưng
    @Query(value = "select u from Employee u where u.id = ?1")
    Employee findById(long id);
    //nhan
    @Query(value = "select u from Employee u where u.users.id= ?1")
    Employee getEmployeeByIdAccount(long id);
//delete của hưng
    @Modifying
    @Query(value = "delete from Employee e WHERE e.id = ?1")
    void deleteEmployeeById(Long id);
//check nhân viên có đang làm hợp đồng hay không của hưng
    @Query(value = "select c from Contract c join Employee e on e.id = c.employee.id where e.id = ?1")
    List<Contract> getAllContract(Long id);

}

