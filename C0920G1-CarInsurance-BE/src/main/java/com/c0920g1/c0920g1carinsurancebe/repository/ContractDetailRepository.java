package com.c0920g1.c0920g1carinsurancebe.repository;

//import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import javax.transaction.Transactional;
import java.util.List;

public interface ContractDetailRepository extends JpaRepository<ContractDetail, Long> {


    //khoa

    @Query(value = "select * from contract_detail where id = ?", nativeQuery = true)
    ContractDetail findByIdQuery(Long id);

    @Query(value = "select * from contract_detail", nativeQuery = true)
    List<ContractDetail> listContractDetail();

    @Query(value = "select * from contract_detail where contract_id= 1?", nativeQuery = true)
    ContractDetail findByContractId(Long contract_id);
    @Modifying
    @Query(value = "update Contract_Detail c  set c.payment =?1 , c.payment_type =?2  where c.contract.id = ?3", nativeQuery = true)
    @Transactional
    void updateContractDetail(@Param("payment") String payment,@Param("payment_type") String payment_type,@Param("id") Long id);









}
