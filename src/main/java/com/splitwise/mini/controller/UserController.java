package com.splitwise.mini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.splitwise.mini.dto.AuthRequestDTO;
import com.splitwise.mini.dto.AuthResponseDTO;
import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.entity.Users;
import com.splitwise.mini.service.UsersService;

@RestController
@RequestMapping("/api/users") 
public class UserController {

    @Autowired
    private UsersService usersService;

    //Registers a new user in the system.
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody Users user) {   
        UserDTO registeredUser = usersService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    //Authenticates a user and generates a JWT token upon successful login.  
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody AuthRequestDTO authRequest) {
        AuthResponseDTO response = usersService.verify(authRequest);
        return ResponseEntity.ok(response);
    }
}
