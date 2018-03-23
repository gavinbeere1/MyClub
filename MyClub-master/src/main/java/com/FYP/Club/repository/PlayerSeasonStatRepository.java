package com.FYP.Club.repository;




import com.FYP.Club.model.PlayerSeasonStat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerSeasonStatRepository extends JpaRepository<PlayerSeasonStat, Long> {

}

