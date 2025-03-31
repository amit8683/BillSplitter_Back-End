package com.splitwise.mini.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.mini.dto.TeamDTO;
import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.entity.Team;
import com.splitwise.mini.entity.TeamMember;
import com.splitwise.mini.entity.Users;
import com.splitwise.mini.repository.TeamMemberRepository;
import com.splitwise.mini.repository.TeamRepository;
import com.splitwise.mini.repository.UsersRepository;

@Service
public class TeamServiceImpl implements TeamService{
	 @Autowired
	    private TeamRepository teamRepository;

	    @Autowired
	    private UsersRepository usersRepository;

	    @Autowired
	    private TeamMemberRepository teamMemberRepository;

	    @Override
	    public TeamDTO createTeam(TeamDTO teamDTO) {
	
	        Users creator = usersRepository.findById(teamDTO.getCreatedBy())
	                .orElseThrow(() -> new RuntimeException("Creator not found"));

	        Team team = new Team();
	        team.setTeamName(teamDTO.getTeamName());
	        team.setCreatedBy(creator);
	        final Team savedTeam = teamRepository.save(team);

	        List<Users> members = new ArrayList<>();
	        
	        if (teamDTO.getMemberEmails() != null && !teamDTO.getMemberEmails().isEmpty()) {
	            members = usersRepository.findByEmailIn(teamDTO.getMemberEmails());

	            if (members.isEmpty()) {
	                throw new RuntimeException("No valid users found for provided emails.");
	            }
	        }

	        members.add(creator);
	        List<TeamMember> teamMembers = members.stream()
	                .map(user -> new TeamMember(savedTeam, user))
	                .collect(Collectors.toList());

	        teamMemberRepository.saveAll(teamMembers);

	        return new TeamDTO(
	            team.getTeamId(),
	            team.getTeamName(),
	            creator.getUserId(),
	            members.stream().map(Users::getEmail).collect(Collectors.toList()) // Return actual saved members
	        );
	    }

	
	@Override
	public List<TeamDTO> getUserTeams(Integer userId) {

		List<Team> createdTeams = teamRepository.findByCreatedBy_UserId(userId);

	    List<Integer> teamIds = teamMemberRepository.findTeamIdsByUserId(userId);
	    
	    List<Team> joinedTeams = teamRepository.findByTeamIdIn(teamIds);

	    Set<Team> allTeams = new HashSet<>(createdTeams);
	    allTeams.addAll(joinedTeams);

	    return allTeams.stream().map(team -> new TeamDTO(
	        team.getTeamId(),
	        team.getTeamName(),
	        team.getCreatedBy().getUserId(),  
	        team.getTeamMembers().stream()    
	            .map(member -> member.getUser().getEmail()) 
	            .collect(Collectors.toList())
	    )).collect(Collectors.toList());
	}

	@Override
    public List<UserDTO> getCreatorsByIds(List<Integer> creatorIds) {
		
        List<Users> users = usersRepository.findByUserIdIn(creatorIds);
        
        return users.stream()
                .map(user -> new UserDTO(user.getUserId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }
	
	@Override
	public TeamDTO getTeamById(Integer teamId) {
	    Team team = teamRepository.findById(teamId)
	            .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));

	    return new TeamDTO(
	            team.getTeamId(),
	            team.getTeamName(),
	            team.getCreatedBy().getUserId()
	           );
	}


}
