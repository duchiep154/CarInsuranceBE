package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.repository.UserRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.UsersRepository;
import com.c0920g1.c0920g1carinsurancebe.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override

    public Users findUsersById(long id) {
        return usersRepository.findById(id).get();
    }

    @Override
    public void save(Users users) {
        usersRepository.save(users);
    }



}
