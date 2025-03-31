package com.splitwise.mini.service;

import com.splitwise.mini.entity.Users;

import java.util.List;

import com.splitwise.mini.dto.AuthRequestDTO;
import com.splitwise.mini.dto.AuthResponseDTO;
import com.splitwise.mini.dto.UserDTO;
public interface UsersService {
	 UserDTO register(Users user);
	    AuthResponseDTO verify(AuthRequestDTO authRequest);
}
