package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.Accident;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.StatusAccident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AccidentRepository extends JpaRepository<Accident, Long> {

    //Quoc bao
    //List accident by status
    @Query(value = "select a from Accident a" +
            " join ContractDetail c on a.contractDetail.id = c.id" +
            " join Contract ct on a.contractDetail.contract.id = ct.id" +
            " join Car car on ct.car.id = car.id" +
            " join Customer cus on car.customer.id = cus.id" +
            " where a.statusAccident.id = :id and a.contractDetail.contract.car.customer.name like %:search%")
    Page<Accident> findAllByIdStatus(Long id, String search, Pageable pageable);

    //find accident by id
    @Query(value = "select a from Accident a" +
            " join ContractDetail c on a.contractDetail.id = c.id" +
            " join Contract ct on c.contract.id = ct.id" +
            " join Car car on ct.car.id = car.id" +
            " join Customer cus on car.customer.id = cus.id" +
            " where a.id = ?1")
    Accident findAccidentById(Long id);

    //update reason not approval where id
    @Modifying
    @Query(value = "update Accident a set a.notApprovedReason = ?1, a.statusAccident = ?2 where a.id = ?3")
    void updateAccidentNotApproval(String notApprovalReason, StatusAccident statusAccident, Long id);

    //update reason not approval where id
    @Modifying
    @Query(value = "update Accident a set a.money = ?1, a.statusAccident = ?2 where a.id = ?3")
    void updateAccidentWasApproval(String money, StatusAccident statusAccident, Long id);

    //cường update status accident sau khi hoàn tiền
    @Modifying
    @Query(value = "update Accident a set a.statusAccident = ?1 where a.id = ?2")
    void updateAccidentWasRefund(StatusAccident statusAccident, Long id);

    //    khoa
    @Modifying
    @Query(
            value =
                    "insert into accident(reason, request_day, img, id_contract_detail, id_status_accident) " +
                            "values (?1, curdate(), ?2, ?3, 1)",
            nativeQuery = true)
    void addListAccident(String reason, String img, Long idContract);


    @Query(value = "select * from car_insurance.accident", nativeQuery = true)
    List<Accident> listAccident();

}
