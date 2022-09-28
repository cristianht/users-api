package com.lab.usersapi.controller;

import com.lab.usersapi.entity.User;
import com.lab.usersapi.repository.UserRepository;
import com.lab.usersapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/apiuser/v1")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String getUsers() {
        return "te devuelvo los usuarios";
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User userAdded = userService.addUser(user);
        if (userAdded != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.LOCATION, String.format("/users/%d", userAdded.getId()));
            return new ResponseEntity<>(userAdded, responseHeaders, HttpStatus.CREATED);
            /*
            return ResponseEntity
                    .created(URI.create(String.format("/users/%d", userAdded.getId())))
                    .body(userAdded);*/
        }
        return ResponseEntity.badRequest().body("Ya existe un usuario con el mismo mail");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
