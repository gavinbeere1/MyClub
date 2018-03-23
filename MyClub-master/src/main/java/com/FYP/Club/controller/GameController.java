package com.FYP.Club.controller;


import com.FYP.Club.model.Game;
import com.FYP.Club.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    // Get All Games
    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    // Create a new Game
    @PostMapping("/games")
    public Game createGame(@Valid @RequestBody Game game) {
        return gameRepository.save(game);
    }

    // Get a Single Game
    @GetMapping("/games/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable(value = "id") Long gameId) {
        Game game = gameRepository.findOne(gameId);
        if(game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(game);
    }

    // Update a Game
    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable(value = "id") Long gameId, 
                                           @Valid @RequestBody Game gameDetails) {
        Game game = gameRepository.findOne(gameId);
        if(game == null) {
            return ResponseEntity.notFound().build();
        }
        game.setDatePlayed(gameDetails.getDatePlayed());
        game.setHomeSide(gameDetails.getHomeSide());
        game.setAwaySide(gameDetails.getAwaySide());
        game.setFinalScore(gameDetails.getFinalScore());

        Game updatedGame = gameRepository.save(game);
        return ResponseEntity.ok(updatedGame);
    }

    // Delete a Note	
    @DeleteMapping("/games/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable(value = "id") Long gameId) {
        Game game = gameRepository.findOne(gameId);
        if(game == null) {
            return ResponseEntity.notFound().build();
        }

        gameRepository.delete(game);
        return ResponseEntity.ok().build();
    }
}
