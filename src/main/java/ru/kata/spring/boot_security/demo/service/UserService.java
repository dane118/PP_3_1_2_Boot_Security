package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();

    void addOrUpdateUser(User user, Set<Role> roleSet);

    void deleteUser(User user);

    User getById(Long id);

    User getUserByEmail(String email);

}
