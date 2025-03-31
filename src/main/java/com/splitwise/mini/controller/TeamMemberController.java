package com.splitwise.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.splitwise.mini.dto.UserDTO;
import com.splitwise.mini.service.TeamMemberService;

@RestController
@RequestMapping("/api/team-members") 
public class TeamMemberController {

    @Autowired 
    private TeamMemberService teamMemberService;

    //Retrieves all members of a given team. 
    @GetMapping("/{teamId}/members") 
    public ResponseEntity<List<UserDTO>> getTeamMembers(@PathVariable Integer teamId) {
        List<UserDTO> teamMembers = teamMemberService.getTeamMembers(teamId);
        return ResponseEntity.ok(teamMembers);
    }
}
