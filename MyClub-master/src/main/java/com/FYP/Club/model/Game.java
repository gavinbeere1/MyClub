package com.FYP.Club.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Game {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long gameId;
	private String datePlayed;
	private String finalScore;
	
	@OneToOne
	private Team homeSide;
	@OneToOne
	private Team awaySide;
	
	
	public Game() {
		
	}

	public Game(Long gameId, String datePlayed, String finalScore, Team homeSide,
			Team awaySide) {
		super();
		this.gameId = gameId;
		this.datePlayed = datePlayed;
		this.finalScore = finalScore;
		this.homeSide = homeSide;
		this.awaySide = awaySide;
	}




	public Team getHomeSide() {
		return homeSide;
	}



	public void setHomeSide(Team homeSide) {
		this.homeSide = homeSide;
	}



	public Team getAwaySide() {
		return awaySide;
	}



	public void setAwaySide(Team awaySide) {
		this.awaySide = awaySide;
	}



	public Long getGameId() {
		return gameId;
	}


	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}


	public String getDatePlayed() {
		return datePlayed;
	}


	public void setDatePlayed(String datePlayed) {
		this.datePlayed = datePlayed;
	}



	public String getFinalScore() {
		return finalScore;
	}


	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}
	
	 
	
	
	

}
