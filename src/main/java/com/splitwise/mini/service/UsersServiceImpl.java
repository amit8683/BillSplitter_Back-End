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

/**
 * Implementation of the UsersService interface.
 * Handles user registration and authentication logic.
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10); // Password hashing strength: 10

    /**
     * Registers a new user by encrypting their password and saving them in the database.
     * 
     * @param user The user entity containing registration details.
     * @return UserDTO containing registered user details (excluding password).
     */
    @Override
    public UserDTO register(Users user) {
        user.setPassword(encoder.encode(user.getPassword())); // Encrypt password before saving
        usersRepository.save(user);
        
        // Convert entity to DTO
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    /**
     * Verifies user credentials and generates a JWT token if authentication is successful.
     * 
     * @param authRequest DTO containing login credentials (email & password).
     * @return AuthResponseDTO containing JWT token and user details.
     * @throws RuntimeException if authentication fails.
     */
    @Override
    public AuthResponseDTO verify(AuthRequestDTO authRequest) {
        Users user = usersRepository.findByEmail(authRequest.getEmail());

        // Authenticate user credentials
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getEmail()); // Generate JWT token
            return new AuthResponseDTO(token, authRequest.getEmail(), user.getUserId(), user.getUsername());
        }

        throw new RuntimeException("Invalid credentials");
    }
}
