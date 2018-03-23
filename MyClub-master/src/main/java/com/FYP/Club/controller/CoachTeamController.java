package com.FYP.Club.controller;


import com.FYP.Club.model.CoachTeam;
import com.FYP.Club.repository.CoachTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CoachTeamController {

    @Autowired
    CoachTeamRepository coachTeamRepository;

    // Get All CoachTeams
    @GetMapping("/coachteams")
    public List<CoachTeam> getAllCoachTeams() {
        return coachTeamRepository.findAll();
    }

    // Create a new CoachTeam
    @PostMapping("/coachteams")
    public CoachTeam createCoachTeam(@Valid @RequestBody CoachTeam coachTeam) {
        return coachTeamRepository.save(coachTeam);
    }

    // Get a Single CoachTeam
    @GetMapping("/coachteams/{id}")
    public ResponseEntity<CoachTeam> getCoachTeamById(@PathVariable(value = "id") Long coachTeamId) {
        CoachTeam coachTeam = coachTeamRepository.findOne(coachTeamId);
        if(coachTeam == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(coachTeam);
    }

    // Update a CoachTeam
    @PutMapping("/coachteams/{id}")
    public ResponseEntity<CoachTeam> updateCoachTeam(@PathVariable(value = "id") Long coachTeamId, 
                                           @Valid @RequestBody CoachTeam coachTeamDetails) {
        CoachTeam coachTeam = coachTeamRepository.findOne(coachTeamId);
        if(coachTeam == null) {
            return ResponseEntity.notFound().build();
        }
        coachTeam.setYear(coachTeamDetails.getYear());
        coachTeam.setReasonWhy(coachTeamDetails.getReasonWhy());
        
        CoachTeam updatedCoachTeam = coachTeamRepository.save(coachTeam);
        return ResponseEntity.ok(updatedCoachTeam);
    }

    // Delete a Note	
    @DeleteMapping("/coachteams/{id}")
    public ResponseEntity<CoachTeam> deleteCoachTeam(@PathVariable(value = "id") Long coachTeamId) {
        CoachTeam coachTeam = coachTeamRepository.findOne(coachTeamId);
        if(coachTeam == null) {
            return ResponseEntity.notFound().build();
        }

        coachTeamRepository.delete(coachTeam);
        return ResponseEntity.ok().build();
    }
}