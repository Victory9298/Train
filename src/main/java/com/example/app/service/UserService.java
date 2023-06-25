package com.example.app.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.app.entity.User;
import com.example.app.repository.TrainRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Transactional
    public User createUser(String commonname, String email) {

        Optional<User> user = repository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        }
        User userEntity = new User();
        userEntity.setCommonname(commonname);
        userEntity.setEmail(email);
        userEntity.setCreatedAt(LocalDateTime.now());
        repository.save(userEntity);
        System.out.println(userEntity.getId());
//        createProfile(userEntity.getId());
        return userEntity;
    }
}
