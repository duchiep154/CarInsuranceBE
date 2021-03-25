package com.c0920g1.c0920g1carinsurancebe.service;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;


import java.util.List;

public interface UsersService {
    List<Users> getAllUsers();

    Users findUsersById(long id);

    void save(Users users);



}
