package com.c0920g1.c0920g1carinsurancebe.repository;


import com.c0920g1.c0920g1carinsurancebe.DTO.ContractDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
    ////////////Cre: nguyen bao//////////////
    //    nguyen bao query car by id
    @Query(value = "select * from car " +
            "where id = ?1",
            nativeQuery = true)
    Car findCarById(Long id);

    //    nguyen bao query contract of car by car id
    @Query(value = "select contract.id from contract " +
            "join car on car.id = contract.car_id " +
            "where car.id = ?1",
            nativeQuery = true)
    List<ContractDTO> getAllContractOfCar(Long id);

    //    nguyen bao query delete car by id
    @Modifying
    @Query(value = "delete from car " +
            "where id = ?1",
            nativeQuery = true)
    void deleteCar(Long id);
}
