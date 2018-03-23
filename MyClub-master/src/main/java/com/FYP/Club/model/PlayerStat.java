package com.FYP.Club.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

//@NamedQueries( {
//	@NamedQuery(name = "PlayerStats.findAll", query = "select o from PlayerStats o"),
//	@NamedQuery(name = "PlayerStats.findById", query = "select o from PlayerStats o where o.id=:id"),
//})


@Entity
public class PlayerStat {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int playerStatId;
	private int triesScored;
	private int gamesPlayed;
	private int metresMade;
	
	public PlayerStat()
	{
		
	}

	public PlayerStat(int triesScored, int gamesPlayed, int metresMade) {
		super();
		this.triesScored = triesScored;
		this.gamesPlayed = gamesPlayed;
		this.metresMade = metresMade;
	}

	public int getPlayerStatId() {
		return playerStatId;
	}

	public void setPlayerStatId(int playerStatId) {
		this.playerStatId = playerStatId;
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
	
	
	

}

