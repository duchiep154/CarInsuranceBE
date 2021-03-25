package com.c0920g1.c0920g1carinsurancebe.repository;


import com.c0920g1.c0920g1carinsurancebe.entities.product.CarType;
import com.c0920g1.c0920g1carinsurancebe.entities.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarTypeRepository extends JpaRepository<CarType, Long> {
    @Query("SELECT carType from CarType carType")
    List<CarType> getAllCarTypeByQuery();


}
