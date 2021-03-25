package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.entities.product.Product;
import com.c0920g1.c0920g1carinsurancebe.entities.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    @Query("SELECT productType from ProductType productType")
    List<ProductType> getAllProductTypeByQuery();
}

