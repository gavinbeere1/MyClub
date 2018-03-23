package com.FYP.Club.controller;


import com.FYP.Club.model.PlayerStat;
import com.FYP.Club.repository.PlayerStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerStatController {

    @Autowired
    PlayerStatRepository playerStatRepository;

    // Get All PlayerStats
    @GetMapping("/playerStats")
    public List<PlayerStat> getAllPlayerStats() {
        return playerStatRepository.findAll();
    }

    // Create a new PlayerStat
    @PostMapping("/playerStats")
    public PlayerStat createPlayerStat(@Valid @RequestBody PlayerStat playerStat) {
        return playerStatRepository.save(playerStat);
    }

    // Get a Single PlayerStat
    @GetMapping("/playerStats/{id}")
    public ResponseEntity<PlayerStat> getPlayerStatById(@PathVariable(value = "id") Long playerStatId) {
        PlayerStat playerStat = playerStatRepository.findOne(playerStatId);
        if(playerStat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerStat);
    }

    // Update a PlayerStat
    @PutMapping("/playerStats/{id}")
    public ResponseEntity<PlayerStat> updatePlayerStat(@PathVariable(value = "id") Long playerStatId, 
                                           @Valid @RequestBody PlayerStat playerStatDetails) {
        PlayerStat playerStat = playerStatRepository.findOne(playerStatId);
        if(playerStat == null) {
            return ResponseEntity.notFound().build();
        }
      playerStat.setTriesScored(playerStatDetails.getTriesScored());
      playerStat.setGamesPlayed(playerStatDetails.getGamesPlayed());
      playerStat.setMetresMade(playerStatDetails.getMetresMade());

        PlayerStat updatedPlayerStat = playerStatRepository.save(playerStat);
        return ResponseEntity.ok(updatedPlayerStat);
    }

    // Delete a Note	
    @DeleteMapping("/playerStats/{id}")
    public ResponseEntity<PlayerStat> deletePlayerStat(@PathVariable(value = "id") Long playerStatId) {
        PlayerStat playerStat = playerStatRepository.findOne(playerStatId);
        if(playerStat == null) {
            return ResponseEntity.notFound().build();
        }

        playerStatRepository.delete(playerStat);
        return ResponseEntity.ok().build();
    }
}