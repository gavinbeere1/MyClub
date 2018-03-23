package com.FYP.Club.controller;

import com.FYP.Club.model.Role;
import com.FYP.Club.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRolesController {

    @Autowired
    RoleRepository roleRepository;

    // Get All UserRolesns
    @GetMapping("/userroles")
    public List<Role> getAllUserRoless() {
        return roleRepository.findAll();
    }

    // Create a new UserRolesn
    @PostMapping("/userroles")
    public Role createUserRoles(@Valid @RequestBody Role role) {
        return roleRepository.save(role);
    }

    // Get a Single UserRoles
    @GetMapping("/userroles/{id}")
    public ResponseEntity<Role> getUserRolesById(@PathVariable(value = "id") Long id) {
        Role role = roleRepository.findOne(id);
        if(role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(role);
    }

    // Update a UserRoles
    @PutMapping("/userroles/{id}")
    public ResponseEntity<Role> updateUserRoles(@PathVariable(value = "id") Long id, 
                                           @Valid @RequestBody Role userRolesDetails) {
        Role role = roleRepository.findOne(id);
        if(role == null) {
            return ResponseEntity.notFound().build();
        }
        role.setRole(userRolesDetails.getRole());
      




        Role updatedUserRoles = roleRepository.save(role);
        return ResponseEntity.ok(updatedUserRoles);
    }

    // Delete a Note	
    @DeleteMapping("/userroles/{id}")
    public ResponseEntity<Role> deleteUserRoles(@PathVariable(value = "id") Long id) {
        Role role = roleRepository.findOne(id);
        if(role == null) {
            return ResponseEntity.notFound().build();
        }

        roleRepository.delete(role);
        return ResponseEntity.ok().build();
    }
}
