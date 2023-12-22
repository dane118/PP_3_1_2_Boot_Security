package ru.kata.spring.boot_security.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService service, RoleService roleService) {
        this.userService = service;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> showAllUsers() {
         return  ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        userService.addOrUpdateUser(user, user.getRoles());
        return ResponseEntity.ok(user);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.addOrUpdateUser(user, user.getRoles());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("User with id = %d not found.", id));
        }
        userService.deleteUser(user);
        return ResponseEntity.ok(String.format("User with id = %d was deleted. ", id));
    }
//
//    @PostMapping("/saveOrUpdateUser")
//    public String saveUser(@Valid @ModelAttribute("user") User user
//            , @RequestParam("selectedRoles") Long[] selectRoles
//            , BindingResult result) {
//
//        if (!result.hasErrors()) {
//            Set<Role> rolesByArrayIds = roleService.getRolesByArrayIds(selectRoles);
//            userService.addOrUpdateUser(user, rolesByArrayIds);
//        }
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/delete")
//    public String deleteUser(@RequestParam("id") Long id) {
//        userService.deleteUser(userService.getById(id));
//        return "redirect:/admin";
//    }
}