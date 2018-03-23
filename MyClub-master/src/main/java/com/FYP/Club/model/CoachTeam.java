package com.FYP.Club.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class CoachTeam {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long coachTeamId;
	private Date year;
	private String reasonWhy;
	
	
	public CoachTeam() {
		
	}
	


	public CoachTeam(Long coachTeamId, Date year, String reasonWhy) {
		super();
		this.coachTeamId = coachTeamId;
		this.year = year;
		this.reasonWhy = reasonWhy;
	}


	public Long getCoachTeamId() {
		return coachTeamId;
	}


	public void setCoachTeamId(Long coachTeamId) {
		this.coachTeamId = coachTeamId;
	}


	public Date getYear() {
		return year;
	}


	public void setYear(Date year) {
		this.year = year;
	}


	public String getReasonWhy() {
		return reasonWhy;
	}


	public void setReasonWhy(String reasonWhy) {
		this.reasonWhy = reasonWhy;
	}
	
	
	
	
	
	
	
	

}
