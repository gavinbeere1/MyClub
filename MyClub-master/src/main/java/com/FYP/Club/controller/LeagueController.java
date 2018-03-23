package com.FYP.Club.controller;



import com.FYP.Club.model.League;
import com.FYP.Club.repository.LeagueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeagueController {

    @Autowired
    LeagueRepository leagueRepository;

    // Get All Leagues
    @GetMapping("/leagues")
    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    // Create a new League
    @PostMapping("/leagues")
    public League createLeague(@Valid @RequestBody League league) {
        return leagueRepository.save(league);
    }

    // Get a Single League
    @GetMapping("/leagues/{id}")
    public ResponseEntity<League> getLeagueById(@PathVariable(value = "id") Long leagueId) {
        League league = leagueRepository.findOne(leagueId);
        if(league == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(league);
    }

    // Update a League
    @PutMapping("/leagues/{id}")
    public ResponseEntity<League> updateLeague(@PathVariable(value = "id") Long leagueId, 
                                           @Valid @RequestBody League leagueDetails) {
        League league = leagueRepository.findOne(leagueId);
        if(league == null) {
            return ResponseEntity.notFound().build();
        }
        league.setLeagueName(leagueDetails.getLeagueName());
        league.setSeason(leagueDetails.getSeason());

        League updatedLeague = leagueRepository.save(league);
        return ResponseEntity.ok(updatedLeague);
    }

    // Delete a Note	
    @DeleteMapping("/leagues/{id}")
    public ResponseEntity<League> deleteLeague(@PathVariable(value = "id") Long leagueId) {
        League league = leagueRepository.findOne(leagueId);
        if(league == null) {
            return ResponseEntity.notFound().build();
        }

        leagueRepository.delete(league);
        return ResponseEntity.ok().build();
    }
}