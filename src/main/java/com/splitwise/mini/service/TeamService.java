package com.splitwise.mini.service;

import java.util.List;

import com.splitwise.mini.dto.TeamDTO;
import com.splitwise.mini.dto.UserDTO;

public interface TeamService {
	 TeamDTO createTeam(TeamDTO teamDTO);
	 List<TeamDTO> getUserTeams(Integer userId);
	List<UserDTO> getCreatorsByIds(List<Integer> creatorIds);
	public TeamDTO getTeamById(Integer teamId);
}
