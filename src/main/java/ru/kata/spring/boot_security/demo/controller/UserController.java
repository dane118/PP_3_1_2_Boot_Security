package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "index";
    }

    @GetMapping("/admin/new")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/admin/saveOrUpdateUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (!result.hasErrors()) {
            service.addOrUpdateUser(user);
        }
        return "redirect:/users/admin";
    }

    @PostMapping("/admin/edit")
    public String updateUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", service.getById(id));
        return "edit";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        service.deleteUser(service.getById(id));
        return "redirect:/users/admin";
    }
}