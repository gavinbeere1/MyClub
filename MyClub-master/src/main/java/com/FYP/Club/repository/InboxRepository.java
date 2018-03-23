package com.FYP.Club.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FYP.Club.model.Inbox;
import com.FYP.Club.model.UserLogin;


@Repository
public interface InboxRepository extends JpaRepository<Inbox, Long> {

	ArrayList<Inbox> findByReceiverName(String email);
	ArrayList<Inbox> findBySenderName(String email);

	//Inbox findByReceiverName(String email);
//	Inbox findBySenderName(String name);
	Inbox findBySenderNameAndReceiverName(String sname, String rname);
	ArrayList<Inbox> findByReceiverNameAndStatus(String name, String status);
}

