package com.FYP.Club.repository;

import com.FYP.Club.model.League;
import com.FYP.Club.model.Team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
	
	League findByLeagueName(String leaguename);
	League findByDivision(String division);

	

}
