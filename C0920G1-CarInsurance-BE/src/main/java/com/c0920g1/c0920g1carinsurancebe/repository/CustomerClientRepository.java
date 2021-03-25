package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CustomerClientRepository extends JpaRepository<Customer, Long> {
    // repo trang
    @Query(value = "select customer from Customer customer " +
            "join Users user on customer.users.id = user.id where user.id = ?1")
    Customer findCustomerByUserId (long id);

    @Modifying
    @Query(value = "select c from Customer c where c.id = ?1")
    Customer findCustomerById(Long id);

    @Query(value = "select u from Users u where u.id = ?1")
    Users findUsersById(Long id);

    @Modifying
    @Query(value = "update Customer c" +
            " set c.name = ?2, c.gender = ?3, c.dateOfBirth = ?4, c.idCard = ?5, c.phone = ?6, c.email = ?7, c.address = ?8, c.city = ?9, c.img = ?10 " +
            "where c.id = ?1", nativeQuery = false)
    void updateCustomer(Long id, String name, String gender, String dateOfBirth, String idCard, String phone,
                        String email, String address, String city, String img);

    @Modifying
    @Query(value = "update Users u" +
            " set u.password = ?2 " +
            "where u.id = ?1", nativeQuery = false)
    void saveUsers(Long id, String password);

    @Query(value = "select customer.idCard from Customer customer " +
            "where not customer.id = ?1")
    List<String> findAllIdCard (long id);

}
