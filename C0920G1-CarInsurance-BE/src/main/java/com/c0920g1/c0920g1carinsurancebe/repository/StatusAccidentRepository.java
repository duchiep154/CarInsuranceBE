package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.StatusAccident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface StatusAccidentRepository extends JpaRepository<StatusAccident, Long> {
}
