package com.solutis.acabouomony.account.service;

import com.solutis.acabouomony.account.model.User;
import com.solutis.acabouomony.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }


    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }


    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
