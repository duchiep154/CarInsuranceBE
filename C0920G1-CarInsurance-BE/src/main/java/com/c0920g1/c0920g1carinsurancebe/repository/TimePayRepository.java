package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.TimePay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimePayRepository extends JpaRepository<TimePay, Long> {
}
