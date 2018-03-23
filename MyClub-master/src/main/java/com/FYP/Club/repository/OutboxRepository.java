package com.FYP.Club.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.FYP.Club.model.Outbox;



@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
	
	Outbox findBySenderNameAndReceiverName(String sender, String receiver);
	
	ArrayList<Outbox> findBySenderName(String email);


}