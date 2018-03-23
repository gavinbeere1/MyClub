package com.FYP.Club.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Outbox {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String senderName;
	private String receiverName;
	private String status;
	private String viewed;
	
	
	
	public Outbox() {
		super();
	}



	public Outbox(Long id, String senderName, String receiverName,
			String status, String viewed) {
		super();
		this.id = id;
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.status = status;
		this.viewed = viewed;
	}


	public String getSenderName() {
		return senderName;
	}




	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getReceiverName() {
		return receiverName;
	}



	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getViewed() {
		return viewed;
	}



	public void setViewed(String viewed) {
		this.viewed = viewed;
	}
	
	
	
	
	
}

