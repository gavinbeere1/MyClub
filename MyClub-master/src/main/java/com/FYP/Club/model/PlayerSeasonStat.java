package com.FYP.Club.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

//@NamedQueries( {
//	@NamedQuery(name = "PlayerSeasonStats.findAll", query = "select o from PlayerSeasonStats o"),
//	@NamedQuery(name = "PlayerSeasonStats", query = "select o from PlayerSeasonStats o where o.id=:id"),
//})

@Entity
public class PlayerSeasonStat {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long playerseasonstatsId;
	private int triesScored;
	private int gamesPlayed;
	private int metresMade;
	private String season;
    private String club;
    private int tacklesMade;

	
	public PlayerSeasonStat()
	{
		
	}
	
	

	public PlayerSeasonStat(Long playerseasonstatsId, int triesScored,
			int gamesPlayed, int metresMade, String season, String club,
			int tacklesMade) {
		super();
		this.playerseasonstatsId = playerseasonstatsId;
		this.triesScored = triesScored;
		this.gamesPlayed = gamesPlayed;
		this.metresMade = metresMade;
		this.season = season;
		this.club = club;
		this.tacklesMade = tacklesMade;
	}



	public Long getPlayerseasonstatsId() {
		return playerseasonstatsId;
	}

	public void setPlayerseasonstatsId(Long playerseasonstatsId) {
		this.playerseasonstatsId = playerseasonstatsId;
	}

	public int getTriesScored() {
		return triesScored;
	}

	public void setTriesScored(int triesScored) {
		this.triesScored = triesScored;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getMetresMade() {
		return metresMade;
	}

	public void setMetresMade(int metresMade) {
		this.metresMade = metresMade;
	}



	public String getSeason() {
		return season;
	}



	public void setSeason(String season) {
		this.season = season;
	}



	public String getClub() {
		return club;
	}



	public void setClub(String club) {
		this.club = club;
	}



	public int getTacklesMade() {
		return tacklesMade;
	}



	public void setTacklesMade(int tacklesMade) {
		this.tacklesMade = tacklesMade;
	}
	
	

}
