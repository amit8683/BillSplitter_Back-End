package com.splitwise.mini.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.entity.TeamMember;
import com.splitwise.mini.repository.TeamMemberRepository;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Override
    public List<UserDTO> getTeamMembers(Integer teamId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByTeam_TeamId(teamId);
        
        return teamMembers.stream()
                .map(member -> new UserDTO(member.getUser().getUserId(), member.getUser().getUsername(), member.getUser().getEmail()))
                .collect(Collectors.toList());
    }
}
