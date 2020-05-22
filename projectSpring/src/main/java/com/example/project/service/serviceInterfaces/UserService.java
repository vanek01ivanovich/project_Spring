package com.example.project.service.serviceInterfaces;

import com.example.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    boolean addUser(User user);
    boolean existsUserByUserName(String userName);
    void updateUser(User user,String oldUserName);
    void deleteUser(User user);
}
