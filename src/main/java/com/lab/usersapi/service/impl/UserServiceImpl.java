package com.lab.usersapi.service.impl;

import com.lab.usersapi.entity.User;
import com.lab.usersapi.repository.UserRepository;
import com.lab.usersapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        // si no existe otro usuario con el mail
        if (!userRepository.existsByEmail(user.getEmail())) {
            return userRepository.save(user);
        }
        return null;
    }
}
