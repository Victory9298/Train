package com.example.app.entity;
//import jakarta.validation.constraints.NotNull;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "user_role")
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @NotNull
//    @Column(name = "role_user")
//    private String role_user;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getRoleUser() {
//        return role_user;
//    }
//
//    public void setRoleUser(String role_user) {
//        this.role_user = role_user;
//    }
//
//
//}


import com.example.app.entity.ERole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
