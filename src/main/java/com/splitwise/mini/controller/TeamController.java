package com.splitwise.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.splitwise.mini.dto.TeamDTO;
import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.service.TeamService;

@RestController
@RequestMapping("/api/teams") // Base URL for all team-related operations
public class TeamController {

    @Autowired
    private TeamService teamService;
    
    
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Integer teamId) {
    	System.out.print("Running"+teamId);
        TeamDTO teamDTO = teamService.getTeamById(teamId);
        return ResponseEntity.ok(teamDTO);
    }

    /**
     * Creates a new team.
     * @param teamDTO Team details received in the request body.
     * @return ResponseEntity containing the created team details.
     */
    @PostMapping("/create")
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
        TeamDTO createdTeam = teamService.createTeam(teamDTO);
        return ResponseEntity.ok(createdTeam);
    }

    /**
     * Retrieves all teams for a given user.
     * @param userId User ID whose teams need to be fetched.
     * @return ResponseEntity containing a list of teams the user is part of.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TeamDTO>> getUserTeams(@PathVariable Integer userId) {
        List<TeamDTO> teams = teamService.getUserTeams(userId);
        return ResponseEntity.ok(teams);
    }

    /**
     * Retrieves details of multiple team creators based on a list of creator IDs.
     * @param creatorIds List of user IDs of team creators.
     * @return ResponseEntity containing a list of user details for the given creator IDs.
     */
    @PostMapping("/creators")
    public ResponseEntity<List<UserDTO>> getCreators(@RequestBody List<Integer> creatorIds) {
        List<UserDTO> creators = teamService.getCreatorsByIds(creatorIds);
        return ResponseEntity.ok(creators);
    }

}
