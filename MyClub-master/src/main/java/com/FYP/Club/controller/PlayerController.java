//package com.FYP.Club.controller;
//
//
//
//
//import com.FYP.Club.model.Player;
//import com.FYP.Club.repository.PlayerRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class PlayerController {
//
//    @Autowired
//    PlayerRepository playerRepository;
//
//    // Get All Players
//    @GetMapping("/players")
//    public List<Player> getAllPlayers() {
//        return playerRepository.findAll();
//    }
//
//    // Create a new Player
//    @PostMapping("/players")
//    public Player createPlayer(@Valid @RequestBody Player player) {
//        return playerRepository.save(player);
//    }
//
//    // Get a Single Player
//    @GetMapping("/players/{id}")
//    public ResponseEntity<Player> getPlayerById(@PathVariable(value = "id") Long playerId) {
//        Player player = playerRepository.findOne(playerId);
//        if(player == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok().body(player);
//    }
//
//    // Update a Player
//    @PutMapping("/players/{id}")
//    public ResponseEntity<Player> updatePlayer(@PathVariable(value = "id") Long playerId, 
//                                           @Valid @RequestBody Player playerDetails) {
//        Player player = playerRepository.findOne(playerId);
//        if(player == null) {
//            return ResponseEntity.notFound().build();
//        }
// 
//        player.setDob(playerDetails.getDob());
//      ;
//        player.setInjured(playerDetails.getInjured());
//        player.setPreviousInjuries(playerDetails.getPreviousInjuries());
//
//
//
//
//
//
//        Player updatedPlayer = playerRepository.save(player);
//        return ResponseEntity.ok(updatedPlayer);
//    }
//
//    // Delete a Note	
//    @DeleteMapping("/players/{id}")
//    public ResponseEntity<Player> deletePlayer(@PathVariable(value = "id") Long playerId) {
//        Player player = playerRepository.findOne(playerId);
//        if(player == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        playerRepository.delete(player);
//        return ResponseEntity.ok().build();
//    }
//}
