package com.FYP.Club.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@NamedQueries( {
//	@NamedQuery(name = "League.findAll", query = "select o from League o"),
//	@NamedQuery(name = "League.findById", query = "select o from League o where o.id=:id"),
//})

@Entity
public class League {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int leagueId;
	private String season;
	private String leagueName;
	private String division;
	
	// league has many matches and is one to many with teams
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	 public Set<Game> games;
	
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	 public Set<Team> teams;

	public League()
	{
		
	}

	public League(String leagueName, String division, String season) {
		super();
		this.season = season;
		this.leagueName = leagueName;
		this.division =  division;
	}
	
	
	
	

	public League(int leagueId, String season, String leagueName,
			String division, Set<Game> games, Set<Team> teams) {
		super();
		this.leagueId = leagueId;
		this.season = season;
		this.leagueName = leagueName;
		this.division = division;
		this.games = games;
		this.teams = teams;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}
	
	   public void addTeam(Team team){
	    	teams.add(team);
	    }
	   
	   public void addGame(Game game){
	    	games.add(game);
	    }
	
	
	
	
}

