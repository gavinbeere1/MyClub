package com.FYP.Club.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FYP.Club.model.PlayerInfo;





@Repository
public interface PlayerInfoRepository extends JpaRepository<PlayerInfo, Long> {
	
	

}