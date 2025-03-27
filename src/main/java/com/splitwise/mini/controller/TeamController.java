package com.splitwise.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwise.mini.dto.TeamDTO;
import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.service.TeamService;

@RestController
@RequestMapping("/teams") 
public class TeamController {
	 @Autowired
	    private TeamService teamService;
	 @PostMapping("/create")
	    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
	        TeamDTO createdTeam = teamService.createTeam(teamDTO);
	        return ResponseEntity.ok(createdTeam); 
	    } 
	 
	 @GetMapping("/{userId}")
	    public ResponseEntity<List<TeamDTO>> getUserTeams(@PathVariable Integer userId) {
	        List<TeamDTO> teams = teamService.getUserTeams(userId);
	        return ResponseEntity.ok(teams);
	    }
	 
	 // ✅ Restore the missing API method
	    @PostMapping("/creators")
	    public ResponseEntity<List<UserDTO>> getCreators(@RequestBody List<Integer> creatorIds) {
	        List<UserDTO> creators = teamService.getCreatorsByIds(creatorIds);
	        return ResponseEntity.ok(creators);
	    }


}
