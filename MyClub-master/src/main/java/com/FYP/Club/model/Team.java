package com.FYP.Club.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "team")

public class Team implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String teamName;
	private int leaguePosition;
	private String teamAddress;
	private String level;
	private String Bio;
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	 public Set<UserLogin> userLogins;

	public Team() {
		
	}

	
	public Team(Long id, String teamName, int leaguePosition,
			String teamAddress, String level, String bio,
			Set<UserLogin> userLogins) {
		super();
		this.id = id;
		this.teamName = teamName;
		this.leaguePosition = leaguePosition;
		this.teamAddress = teamAddress;
		this.level = level;
		this.Bio = bio;
		this.userLogins = userLogins;
	}



	public String getBio() {
		return Bio;
	}

	public void setBio(String bio) {
		Bio = bio;
	}

	public Set<UserLogin> getUserLogins() {
		return userLogins;
	}



	public void setUserLogin(Set<UserLogin> userLogins) {
		this.userLogins = userLogins;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getLeaguePosition() {
		return leaguePosition;
	}

	public void setLeaguePosition(int leaguePosition) {
		this.leaguePosition = leaguePosition;
	}



	public String getTeamAddress() {
		return teamAddress;
	}

	
	public void setTeamAddress(String teamAddress) {
		this.teamAddress = teamAddress;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public void addUserLogin(UserLogin userLogin){
		userLogins.add(userLogin);
	}
	// new method to remove users
	public void removeUserLogin(UserLogin userLogin){
		userLogins.remove(userLogin);
	}
	
	

}
