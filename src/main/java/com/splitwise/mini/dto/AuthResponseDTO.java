package com.splitwise.mini.dto;

import lombok.Data;

public class AuthResponseDTO {
    private String token;
    private String username; 
    private Integer userId;
    private String email;

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public AuthResponseDTO(String token,String email,Integer userId, String username ) {
	  this.email=email;
	  this.token=token;
	  this.username=username;
	  this.userId=userId;
	}

	// Getter
    public String getToken() {
        return token;
    }

    // Setter (if needed)
    public void setToken(String token) {
        this.token = token;
    }
}
