package com.FYP.Club.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.FYP.Club.model.Team;
import com.FYP.Club.model.UserLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	
	Team findByTeamName(String teamname);
	


//	ArrayList<UserLogin> getUserLogins();
	

	

}

