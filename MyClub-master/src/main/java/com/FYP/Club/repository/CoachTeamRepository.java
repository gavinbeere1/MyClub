package com.FYP.Club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.FYP.Club.model.CoachTeam;

@Repository
public interface CoachTeamRepository extends JpaRepository<CoachTeam, Long>{

}
