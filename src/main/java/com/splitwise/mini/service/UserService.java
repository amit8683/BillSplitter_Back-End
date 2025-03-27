package com.splitwise.mini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.splitwise.mini.entity.Users;
import com.splitwise.mini.repository.UsersRepository;


@Service
public class UserService {
	@Autowired
 private UsersRepository usersRepository;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
	
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return usersRepository.save(user); 
	}
	
	 public List<Users> getAllUsers() {
	        return usersRepository.findAll();
	    }

	public String verify(Users user) {
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());
		}
		return "false"; 
	}
}

