package com.snetwork.services;

import com.snetwork.entities.User;
import com.snetwork.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }
}
