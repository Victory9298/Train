package com.example.app.repository;

import com.example.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

//    @Query("SELECT r.role_user FROM User AS uc " +
//            "JOIN user_role AS r ON uc.roleID = r.id " +
//            "WHERE uc.userID=?1")
    List<String> findAllRoleById(Integer id);

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
