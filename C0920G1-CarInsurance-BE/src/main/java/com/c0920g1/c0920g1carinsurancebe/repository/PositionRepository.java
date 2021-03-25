package com.c0920g1.c0920g1carinsurancebe.repository;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query(value = "select u from Position u")
    List<Position> findAll();
}
