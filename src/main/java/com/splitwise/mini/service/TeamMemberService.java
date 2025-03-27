package com.splitwise.mini.service;

import java.util.List;

import com.splitwise.mini.dto.UserDTO;

public interface TeamMemberService {
	 List<UserDTO> getTeamMembers(Integer teamId);

}
