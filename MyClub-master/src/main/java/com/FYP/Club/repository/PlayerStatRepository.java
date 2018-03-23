package com.FYP.Club.repository;

import com.FYP.Club.model.PlayerStat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerStatRepository extends JpaRepository<PlayerStat, Long> {

}
