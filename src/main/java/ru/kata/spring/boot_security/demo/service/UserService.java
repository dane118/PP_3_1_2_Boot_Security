package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void addOrUpdateUser(User user, Set<Role> rolesByArrayIds);

    void deleteUser(User user);

    User getById(Long id);

    User getUserByEmail(String email);

}
