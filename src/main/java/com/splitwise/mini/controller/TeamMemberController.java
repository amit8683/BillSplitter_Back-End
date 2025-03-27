package com.splitwise.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.service.TeamMemberService;

@RestController
@RequestMapping("/teamMembers")
public class TeamMemberController {
	
	 @Autowired 
	 private TeamMemberService teamMemberService;
	
	 @GetMapping("/team/{teamId}") 
	    public ResponseEntity<List<UserDTO>> getTeamMembers(@PathVariable Integer teamId) {
	        List<UserDTO> teamMembers = teamMemberService.getTeamMembers(teamId);
	        return ResponseEntity.ok(teamMembers);
	    }

}
