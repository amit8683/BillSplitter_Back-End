package com.splitwise.mini.service;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.splitwise.mini.dto.AuthRequestDTO;
import com.splitwise.mini.dto.AuthResponseDTO;
import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.entity.Users;
import com.splitwise.mini.repository.UsersRepository;


@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10); 

 
    @Override
    public UserDTO register(Users user) {
        user.setPassword(encoder.encode(user.getPassword())); // Encrypt password before saving
        usersRepository.save(user);
        
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public AuthResponseDTO verify(AuthRequestDTO authRequest) {
        Users user = usersRepository.findByEmail(authRequest.getEmail());

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getEmail()); // Generate JWT token
            return new AuthResponseDTO(token, authRequest.getEmail(), user.getUserId(), user.getUsername());
        }

        throw new RuntimeException("Invalid credentials");
    }
}
