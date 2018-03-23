package com.FYP.Club.controller;




import com.FYP.Club.model.Team;
import com.FYP.Club.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    // Get All Teams
    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Create a new Team
    @PostMapping("/teams")
    public Team createTeam(@Valid @RequestBody Team team) {
        return teamRepository.save(team);
    }

    // Get a Single Team
    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable(value = "id") Long teamId) {
        Team team = teamRepository.findOne(teamId);
        if(team == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(team);
    }

    // Update a Team
    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable(value = "id") Long teamId, 
                                           @Valid @RequestBody Team teamDetails) {
        Team team = teamRepository.findOne(teamId);
        if(team == null) {
            return ResponseEntity.notFound().build();
        }
        team.setTeamName(teamDetails.getTeamName());
        team.setLeaguePosition(teamDetails.getLeaguePosition());
       

        Team updatedTeam = teamRepository.save(team);
        return ResponseEntity.ok(updatedTeam);
    }

    // Delete a Note	
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Team> deleteTeam(@PathVariable(value = "id") Long teamId) {
        Team team = teamRepository.findOne(teamId);
        if(team == null) {
            return ResponseEntity.notFound().build();
        }

        teamRepository.delete(team);
        return ResponseEntity.ok().build();
    }
}
