package com.c0920g1.c0920g1carinsurancebe.repository;

import com.c0920g1.c0920g1carinsurancebe.entities.account.ERole;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

// Tran Minh Chien
@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select u from Role u where u.name = ?1")
    Optional<Role> findByName(ERole name);
}
