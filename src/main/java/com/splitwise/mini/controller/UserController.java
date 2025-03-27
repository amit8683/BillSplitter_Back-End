package com.splitwise.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.splitwise.mini.dto.AuthRequestDTO;
import com.splitwise.mini.dto.AuthResponseDTO;
import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.entity.Users;
import com.splitwise.mini.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*") // Base path for all user-related APIs
public class UserController {
    @Autowired
    private  UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody Users user) {
    	
        UserDTO registeredUser = usersService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
    	System.out.print(authRequest);
        AuthResponseDTO response = usersService.verify(authRequest);
        return ResponseEntity.ok(response);
    }


}
