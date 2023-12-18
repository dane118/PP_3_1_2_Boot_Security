package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    private String role;
//        @ManyToMany(mappedBy = "roles")
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"))
    private Set<User> userSet;

    public Role() {
    }

    public Role(String role, Set<User> userSet) {
        this.role = role;
        this.userSet = userSet;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Role(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    @Override
    public String toString() {
        return "Role{" +
               "id=" + id +
               ", role='" + role + '\'' +
               ", userSet=" + userSet +
               '}';
    }
}