package com.c0920g1.c0920g1carinsurancebe.repository;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

//Tran Minh Chien.
@Transactional
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

//    Tim User bang username
    @Query(value = "SELECT u FROM Users u WHERE u.username = ?1")
    Optional<Users> findByUsername(String username);

    //    Tim User bang id
    @Query(value = "SELECT u FROM Users u WHERE u.id = ?1")
    Optional<Users> findById(Long id);

    Boolean existsUsersByUsername(String username);

    @Query(value = "SELECT u FROM Users u WHERE u.id = ?1")
    Users getUserById(long id);

    @Query(value = "update Users u set u.password = ?1 where u.id = ?2")
    void updatePasswordUser(String password);

}

