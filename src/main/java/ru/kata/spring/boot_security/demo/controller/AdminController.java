package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userRoles", roles);
        model.addAttribute("userAuth", userService.getUserByEmail(principal.getName()));
        model.addAttribute("listRoles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "index";
    }

    @PostMapping("/saveOrUpdateUser")
    public String saveUser(@Valid @ModelAttribute("user") User user
            , BindingResult result
            , @RequestParam("selectedRoles") Long[] selectRoles) {

        user.setRoles(roleService.getRolesByArrayIds(selectRoles));
        if (!result.hasErrors()) {
            userService.addOrUpdateUser(user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(userService.getById(id));
        return "redirect:/admin";
    }
}