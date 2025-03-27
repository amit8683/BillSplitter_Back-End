package com.splitwise.mini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.splitwise.mini.entity.Users;
import com.splitwise.mini.repository.UsersRepository;
import com.splitwise.mini.security.UserPrincipal;

@Service
public class UsersDetailService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user =usersRepository.findByEmail(email);
		if(user==null) {
			System.out.println("User Not found"); 
			throw new UsernameNotFoundException("user not found");
		}
		return new UserPrincipal(user);
	}


}
