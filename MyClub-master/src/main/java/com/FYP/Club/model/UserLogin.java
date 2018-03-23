package com.FYP.Club.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class UserLogin {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private Long phone;
	private String userName;
	private String address;
	private String password;
	private boolean userStatus;
	private String userType;
	private String position;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private PlayerInfo playerinfo;

	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	public Set<Role> roles;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	 public Set<PlayerSeasonStat> playerStats;
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	 public Set<Inbox> inbox;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	 public Set<Outbox> outbox;

	
	public UserLogin()
	{
		
	}


	public UserLogin(Long id, String firstName, String lastName, Long phone,
			String userName, String address, String password,
			boolean userStatus, String userType, String position,
			Set<Role> roles, Set<PlayerSeasonStat> playerStats) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.userName = userName;
		this.address = address;
		this.password = password;
		this.userStatus = userStatus;
		this.userType = userType;
		this.position = position;
		this.roles = roles;
		this.playerStats = playerStats;
		
		
	}

	
	
	public UserLogin(Long id, String firstName, String lastName, Long phone,
			String userName, String address, String password,
			boolean userStatus, String userType, String position,
			Set<Role> roles, Set<PlayerSeasonStat> playerStats,
			Set<Inbox> inbox, Set<Outbox> outbox) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.userName = userName;
		this.address = address;
		this.password = password;
		this.userStatus = userStatus;
		this.userType = userType;
		this.position = position;
		this.roles = roles;
		this.playerStats = playerStats;
		this.inbox = inbox;
		this.outbox = outbox;
		
	}




	public UserLogin(Long id, String firstName, String lastName, Long phone,
			String userName, String address, String password,
			boolean userStatus, String userType, String position,
			PlayerInfo playerinfo, Set<Role> roles,
			Set<PlayerSeasonStat> playerStats, Set<Inbox> inbox,
			Set<Outbox> outbox) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.userName = userName;
		this.address = address;
		this.password = password;
		this.userStatus = userStatus;
		this.userType = userType;
		this.position = position;
		this.playerinfo = playerinfo;
		this.roles = roles;
		this.playerStats = playerStats;
		this.inbox = inbox;
		this.outbox = outbox;
	}


	public PlayerInfo getPlayerinfo() {
		return playerinfo;
	}


	public void setPlayerinfo(PlayerInfo playerinfo) {
		this.playerinfo = playerinfo;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public Set<Inbox> getInbox() {
		if (inbox==null)
		{
			inbox = new HashSet<>();
		}
		return inbox;
	}


	public void setInbox(Set<Inbox> inbox) {
		this.inbox = inbox;
	}


	public Set<Outbox> getOutbox() {
		if (outbox==null)
		{
			outbox = new HashSet<>();
		}
		return outbox;
	}


	public void setOutbox(Set<Outbox> outbox) {
		this.outbox = outbox;
	}


	public Set<Role> getRoles() {
		if (roles==null)
			roles = new HashSet<>();
		
		
		return roles;
	}

	public void setRole(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}

	public Set<PlayerSeasonStat> getPlayerStats() {
		return playerStats;
	}

	public void setPlayerStats(Set<PlayerSeasonStat> playerStats) {
		this.playerStats = playerStats;
	}

	
	public void addPlayerStat(PlayerSeasonStat pStat)
	{
		playerStats.add(pStat);
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}
	
	public void addRole(Role role){

		getRoles().add(role);
	}
	
	public void addInbox(Inbox i){
		getInbox().add(i);
	}

	public void addOutbox(Outbox i){
		getOutbox().add(i);
	}



}
