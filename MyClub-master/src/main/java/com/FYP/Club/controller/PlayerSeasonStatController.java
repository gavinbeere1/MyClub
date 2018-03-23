package com.FYP.Club.controller;




import com.FYP.Club.model.PlayerSeasonStat;
import com.FYP.Club.repository.PlayerSeasonStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerSeasonStatController {

    @Autowired
    PlayerSeasonStatRepository playerSeasonStatRepository;

    // Get All PlayerSeasonStats
    @GetMapping("/playerseasonstats")
    public List<PlayerSeasonStat> getAllPlayerSeasonStats() {
        return playerSeasonStatRepository.findAll();
    }

    // Create a new PlayerSeasonStat
    @PostMapping("/playerseasonstats")
    public PlayerSeasonStat createPlayerSeasonStat(@Valid @RequestBody PlayerSeasonStat playerSeasonStat) {
        return playerSeasonStatRepository.save(playerSeasonStat);
    }

    // Get a Single PlayerSeasonStat
    @GetMapping("/playerseasonstats/{id}")
    public ResponseEntity<PlayerSeasonStat> getPlayerSeasonStatById(@PathVariable(value = "id") Long playerSeasonStatId) {
        PlayerSeasonStat playerSeasonStat = playerSeasonStatRepository.findOne(playerSeasonStatId);
        if(playerSeasonStat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerSeasonStat);
    }

    // Update a PlayerSeasonStat
    @PutMapping("/playerseasonstats/{id}")
    public ResponseEntity<PlayerSeasonStat> updatePlayerSeasonStat(@PathVariable(value = "id") Long playerSeasonStatId, 
                                           @Valid @RequestBody PlayerSeasonStat playerSeasonStatDetails) {
        PlayerSeasonStat playerSeasonStat = playerSeasonStatRepository.findOne(playerSeasonStatId);
        if(playerSeasonStat == null) {
            return ResponseEntity.notFound().build();
        }
        playerSeasonStat.setTriesScored(playerSeasonStatDetails.getTriesScored());
        playerSeasonStat.setGamesPlayed(playerSeasonStatDetails.getGamesPlayed());
        playerSeasonStat.setMetresMade(playerSeasonStatDetails.getMetresMade());

        PlayerSeasonStat updatedPlayerSeasonStat = playerSeasonStatRepository.save(playerSeasonStat);
        return ResponseEntity.ok(updatedPlayerSeasonStat);
    }

    // Delete a Note	
    @DeleteMapping("/playerseasonstats/{id}")
    public ResponseEntity<PlayerSeasonStat> deletePlayerSeasonStat(@PathVariable(value = "id") Long playerSeasonStatId) {
        PlayerSeasonStat playerSeasonStat = playerSeasonStatRepository.findOne(playerSeasonStatId);
        if(playerSeasonStat == null) {
            return ResponseEntity.notFound().build();
        }

        playerSeasonStatRepository.delete(playerSeasonStat);
        return ResponseEntity.ok().build();
    }
}