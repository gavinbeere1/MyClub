package com.FYP.Club.controller;

import com.FYP.Club.model.UserLogin;
import com.FYP.Club.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserLoginController {

    @Autowired
    UserLoginRepository userLoginRepository;

    // Get All UserLoginns
    @GetMapping("/userlogins")
    public List<UserLogin> getAllUserLogins() {
        return userLoginRepository.findAll();
    }

    // Create a new UserLoginn
    @PostMapping("/userlogins")
    public UserLogin createUserLogin(@Valid @RequestBody UserLogin userLogin) {
        return userLoginRepository.save(userLogin);
    }

    // Get a Single UserLogin
    @GetMapping("/userlogins/{id}")
    public ResponseEntity<UserLogin> getUserLoginById(@PathVariable(value = "id") Long id) {
        UserLogin userLogin = userLoginRepository.findOne(id);
        if(userLogin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userLogin);
    }

    // Update a UserLogin
    @PutMapping("/userlogins/{id}")
    public ResponseEntity<UserLogin> updateUserLogin(@PathVariable(value = "id") Long id, 
                                           @Valid @RequestBody UserLogin userLoginDetails) {
        UserLogin userLogin = userLoginRepository.findOne(id);
        if(userLogin == null) {
            return ResponseEntity.notFound().build();
        }
        userLogin.setPhone(userLoginDetails.getPhone());
        userLogin.setFirstName(userLoginDetails.getFirstName());
        userLogin.setLastName(userLoginDetails.getLastName());
        userLogin.setUserName(userLoginDetails.getUserName());
        userLogin.setAddress(userLoginDetails.getAddress());
        userLogin.setPassword(userLoginDetails.getPassword());
        userLogin.setUserType(userLoginDetails.getUserType());





        UserLogin updatedUserLogin = userLoginRepository.save(userLogin);
        return ResponseEntity.ok(updatedUserLogin);
    }

    // Delete a Note	
    @DeleteMapping("/userlogins/{id}")
    public ResponseEntity<UserLogin> deleteUserLogin(@PathVariable(value = "id") Long id) {
        UserLogin userLogin = userLoginRepository.findOne(id);
        if(userLogin == null) {
            return ResponseEntity.notFound().build();
        }

        userLoginRepository.delete(userLogin);
        return ResponseEntity.ok().build();
    }
}
