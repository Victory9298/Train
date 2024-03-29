package com.example.app.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import jakarta.persistence.*;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//@Entity
//@Table
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    @NotNull
//    @Column(name = "commonname")
//    private String commonname;
//
//    @NotNull
//    @Column(name = "email", unique = true)
//    private String email;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public String getCommonname() {
//        return commonname;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCommonname(String commonname) {
//        this.commonname = commonname;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//}

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
