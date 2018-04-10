package com.FYP.Club.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.NonUniqueResultException;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.FYP.Club.Notification;
import com.FYP.Club.services.NotificationService;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FYP.Club.model.Game;
import com.FYP.Club.model.Inbox;
import com.FYP.Club.model.League;
import com.FYP.Club.model.Outbox;
import com.FYP.Club.model.PlayerInfo;
import com.FYP.Club.model.Role;
import com.FYP.Club.model.Team;
import com.FYP.Club.model.UserLogin;
import com.FYP.Club.repository.GameRepository;
import com.FYP.Club.repository.InboxRepository;
import com.FYP.Club.repository.LeagueRepository;
import com.FYP.Club.repository.OutboxRepository;
import com.FYP.Club.repository.PlayerInfoRepository;
import com.FYP.Club.repository.RoleRepository;
import com.FYP.Club.repository.TeamRepository;
import com.FYP.Club.repository.UserLoginRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize
@SessionAttributes("username")
@Controller
public class HomeController {
 

	 boolean ok;
    @Autowired
    PlayerInfoRepository playerRepository;
	 
    @Autowired 
    OutboxRepository oR;    
    
    @Autowired
    InboxRepository iR;
    
	@Autowired
	RoleRepository roleRepository;

	  @Autowired
		UserLoginRepository userRepository;
	
	  @Autowired
			TeamRepository teamRepository;
	  
	  @Autowired
		GameRepository gameRepository;
	  
	   
	  @Autowired
			LeagueRepository leagueRepository;
	 
	  @RequestMapping(value = "/test")
	    public String index() {
	        return "test";
	    }
	 
	  //websocket

	  @Autowired
	  private NotificationService notificationService;

	  /**
	   * GET  /  -> show the index page.
	   */
	  @RequestMapping("/index2")
	  public String index2() {
	    return "index2";
	  }
	 
	  ///testing this method
	  
	  @RequestMapping(value = "/viewmyteam", method=RequestMethod.GET)
		public String viewMyTeam(Model model) {

		  Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

//	      UserLogin user = userRepository.findByUserName(email);
	      
	      
	      ArrayList<Team> teams = (ArrayList<Team>) teamRepository.findAll();
		   
		  for (Team t : teams)
		  {
			  for(UserLogin ul : t.getUserLogins())
			  {
				  if(ul.getUserName().equals(email))
				  {
				      model.addAttribute("myteam", t); 
				      break;

				  }
			  }
		  }
			
		     return "myteam";
	  }
	  //Since I am displaying all in a table anyways, maybe use a search bar implementing JS to search and display a team
	  //https://codepen.io/adobewordpress/pen/gbewLV
	  //https:www.w3schools.com/howto/tryit.asp?filename=tryhow_js_filter_table
	  @RequestMapping(value = "/saveAreaToProfile",  method=RequestMethod.GET)
		public String saveAreaToProfile(Model model, 
				@RequestParam("areaName") String areaName, RedirectAttributes redirectAttributes) {

		  Team searchTeam =  teamRepository.findByTeamName(areaName);
		  
		      if (searchTeam == null)
		      {
		    	  System.out.println("NULL");
		    	  String invalid = "No such team";
		    	  model.addAttribute("searchTeam", invalid);
		      }
		      else
		      {

//		      model.addAttribute("searchTeam", searchTeam); 
		      
		      redirectAttributes.addFlashAttribute("searchTeam", searchTeam);
				redirectAttributes.addFlashAttribute("message","Added successfully.");
		    	  System.out.println(searchTeam.getTeamName());

		      }
			
		      return "redirect:/showteams";
		}
	 
	  /**
	   * GET  /notifications  -> show the notifications page.
	   */
	  @RequestMapping("/notifications")
	  public String notifications() {
	    return "notifications";
	  }
	  /**
	   * POST  /some-action  -> do an action.
	   * 
	   * After the action is performed will be notified UserA.
	   */
	  @RequestMapping(value = "/some-action", method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity<?> someAction(@RequestParam("teamName") String tname) {

		  
		////This gets user (outbox i.e. sending invitation)//////
				  Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
			      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

			      UserLogin user = userRepository.findByUserName(email);
			      String name = user.getUserName();
			      String fname = user.getFirstName();
			      String sname = user.getLastName();
			      
			      
			      
			      Outbox out = new Outbox();
			     
			     
			      out.setSenderName(name);
			      out.setStatus("Pending");
			      out.setViewed("Unseen");
			      
			      
			      
			      Inbox in = new Inbox();
			      in.setStatus("Pending");
			      in.setViewed("Unseen");
			      in.setSenderName(name);

		          ////////////////////////////////////
			      //https://stackoverflow.com/questions/34691579/responseentity-how-to-obtain-the-body-in-html   (EXCEPTION HANDLING)
			      //Gonna try and to disallow duplicate inbox's here, this works, need to display a rejection though ?
			      
			
			      ///////////////////////////////////
				  //// This is searching the team for a manager (inbox i.e. Recieving invitation)
				  Team team1 = teamRepository.findByTeamName(tname);
				  
				  
				  Set<UserLogin> userLogins = team1.getUserLogins();
				  for (UserLogin s : userLogins) {
					  
					
					  
					  if (s.getUserType().equals("Manager"))
					  {
					      if (oR.findBySenderNameAndReceiverName(name, s.getUserName()) != null)
					      {
					    	  //testing comments
					    	  //System.out.println("AAAAAAAAAAAA If works does the break?");
					    	  return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
					      }
					      else if((s.getUserType().equals("Manager")) && (loggedInUser.getName().equals(s.getUserName())))
						  {
					    	  //Manager cant apply to his own team
					    	  return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
						  }
					      else
					      {
					    	  //System.out.println("AAAAAAAAA It doesnt work...?");
					     
						  out.setReceiverName(s.getUserName());					 
						  in.setReceiverName(s.getUserName());
						  s.addInbox(in);
						  iR.save(in);
						  userRepository.save(s);
						 
					      }
					    notificationService.notify(
					    		
					  	      new Notification("---------New Reguest!--------- \nUserName: " + name + "\nName: " + fname + " " + sname + "\nWants to join your team."), // notification object
					  	    s.getUserName()                  // username
					  	    );
					  }

					}

				  user.addOutbox(out);			
				  oR.save(out);			  
				  userRepository.save(user);
				 

	    return new ResponseEntity<>(HttpStatus.OK);
	  }
	  /////////////////////////////
	  //3 methods below are for the login pages
	  
	   @RequestMapping(value={"/","home"})
       public String home(){
           return "home";
       }
	  

	  
	   
   @RequestMapping(value={"/welcome"})
   public String welcome(){
	   
	      Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

	      UserLogin user = userRepository.findByUserName(email);
	      
	      String result = null;
	      
	      if (user.getUserType().equals("Player"))
			{
				
				
				result = ("welcome");
				//do whats mentioned below for all the entities
				//players result page should ask the player more questions (see the player model for these attributes, then save them to this "player" and point the player to the welcome page
			}

			if (user.getUserType().equals("Manager"))
			{
			
				//here i will try and add a user role (admin) to the manager entity so they can login as admin
				result = ("managerResult");
			}
			
			return result;
   }
 
   @RequestMapping(value="/admin")
   public String admin(){
       return "admin";
   }
  
   @RequestMapping(value={"/login"})
   public String login(){
       return "login";
   }
  
 
  
   @RequestMapping(value="/403")
   public String Error403(){
       return "403";
   }


//@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Order")  // 404
//public class UserAlreadyAppliedException extends RuntimeException {
//
//
//
//}

   //below 2 methods are being tested
   ///@PathVariable("team") Long teamId, before principal
   
   @RequestMapping(value = "/acceptplayer/{id:.+}", method={RequestMethod.POST, RequestMethod.GET})
   public String AcceptPlayer(Principal principal, @PathVariable String id) throws ConcurrentModificationException{

	  ///Need to check here if a player has already been accepted by a manager
	   
	   Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

	  
	   UserLogin requestedUser = userRepository.findByUserName(id);
	   
	
		   
	  ArrayList<Inbox> SendersInbox = (ArrayList<Inbox>) iR.findBySenderName(requestedUser.getUserName()); 

	  for (Inbox i : SendersInbox)
	  {
		 
		  if (i.getStatus().equals("Accepted"))
		  {
			  ok = true;
			  System.out.println("Status = Accepted");
			  break;
		  }
		  else
		  {
			  ok = true;
			  System.out.println("boolean is true, user will be accepted");

		  }
	  }
	  

      if (ok != false)//always will be
      {
    	  System.out.print("boolean is not false");
	   ArrayList<Team> teams = (ArrayList<Team>) teamRepository.findAll();
	   
	  for (Team t : teams)
	  {
		  for(UserLogin ul : t.getUserLogins())
		  {
			  if (ul == null)
			  {
				  System.out.println("AAAAAAAAAAAA + NULL CHECKER");
			  }
			 
			  else if (ul.getUserType().equals("Manager") && ul.getUserName().equals(email))
			  {
				 Inbox inbox = iR.findBySenderNameAndReceiverName(requestedUser.getUserName(), ul.getUserName());

				 Outbox outbox = oR.findBySenderNameAndReceiverName(requestedUser.getUserName(), ul.getUserName());
				 
				  if ((inbox != null) && (!requestedUser.getPosition().equals("True")))
				  {
					  
					  outbox.setStatus("Accepted");
					  System.out.println("Accepted");
					  oR.save(outbox);
					  
					  inbox.setStatus("Accepted");
					  System.out.println("Accepted");
					  iR.save(inbox);   
					  
					  //below is commented out until user approves
//					  requestedUser.setUserStatus(true);
//					  requestedUser.setPosition("True");
//					  t.addUserLogin(requestedUser);
//						 teamRepository.save(t);
						 
						 
						 break;
				  }
				  else
				  {
					  inbox.setStatus("Accepted");
					  System.out.println("Accepted");
					  iR.save(inbox);
					 
				  }
			  } 
		  }
	  }

      } 

      return "inbox";
   }
  
//Unused method below (User has to apply now before joining a team) only for manager
   @RequestMapping(value = "/jointeam/{id}", method={RequestMethod.POST, RequestMethod.GET})
   public String joinTeam(Principal principal, @PathVariable Long id) {


      Team team = teamRepository.findOne(id);
    
      Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

      UserLogin user = userRepository.findByUserName(email);

     user.setPosition("True");
//     if (teamRepository.findByUserLogin(email) != null) throw new UserAlreadyAppliedException();
      //here i should error check to see if the user is associated with the team if not return an error message
      
      //A simpler way to handling join requests from a player would be to:
      //Have a "join request" entity with player
     
   
      team.addUserLogin(user);      
      teamRepository.save(team);
      
      
      return "redirect:/showteams";
   }
  
 
 
   @RequestMapping(value="/viewteam/{id}", method=RequestMethod.GET)
   public String ViewTeam(Model model, @PathVariable Long id) {
	   

	 
	   Team team = teamRepository.findOne(id);
	   
	//the code below should ideally return all the players associated with the above team id in an array list, this array list will be passed via thymeleaf to display all players
	   

	   model.addAttribute("team", team);
	   
	  
	   return "singleteam";
   }
 

   @RequestMapping(value="/showteams", method=RequestMethod.GET)
   public String teams(Model model)
   {
	   ArrayList<Team> teams = (ArrayList<Team>) teamRepository.findAll();
	  
	   model.addAttribute("teams", teams);
	  
	   return "teams";
   }
   
   @RequestMapping(value="/showteams2", method=RequestMethod.GET)
   public String aYeh()
   {

	   return "teams2";

   }

   @RequestMapping(value="/all", method=RequestMethod.GET)
   @ResponseBody
	public List<Team> getResource() {
		
	   List<Team> cueList = teamRepository.findAll();
	   
	   
		return cueList;
	}
  
  
   @RequestMapping(value="/viewplayer/{id}", method=RequestMethod.GET)
   public String ViewPlayer(Model model, @PathVariable Long id) {
	   
	   UserLogin player = userRepository.findOne(id);
	   model.addAttribute("player", player);
	   
	   return "singleplayer2";
   }
  
   @RequestMapping(value="/viewsender/{name:.+}", method=RequestMethod.GET)
   public String ViewSender(Model model, @PathVariable String name) throws NonUniqueResultException {
	  

	   //this method works, although returns "null" if theres duplicate users
	   //wont work for ***@gmail.com address's (FIXED: truncating the name, see above {name:.+})
	   //Going to attempt to change the status of the notification to seen once a view player is clicked
	   Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

	   
	   
	   Inbox inbox = iR.findBySenderNameAndReceiverName(name, email);
	   inbox.setViewed("Seen");
	   ///
	   iR.save(inbox);
	   
	   UserLogin player = userRepository.findByUserName(name);
	   if (player != null){
	   model.addAttribute("player", player);
	   }
	   else
	   {
	   System.out.println("Player is null");
	   }
	   return "singleplayer";
   }
   
   @RequestMapping(value="/deletemessage/{name:.+}", method=RequestMethod.GET)
   public String DeletePlayer(Model model, @PathVariable String name) {

		  
//	      model.addAttribute("inboxs", inboxs);
	  
	   //this method works, although returns "null" if theres duplicate users
	   //wont work for ***@gmail.com address's (FIXED: truncating the name, see above {name:.+})
	   //Going to attempt to change the status of the notification to seen once a view player is clicked
	   
	   Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

	   Inbox inbox = iR.findBySenderNameAndReceiverName(name, email);
	   
	   if (inbox.getStatus().equals("Accepted"))
	   {
		   
	   }
	   else
	   {
		   inbox.setStatus("Declined");
	  
	   		iR.save(inbox);

	   		Outbox outbox = oR.findBySenderNameAndReceiverName(name, email);
	   		
	   		outbox.setStatus("Declined");
	   		
	   		oR.save(outbox);
	   		
	   		UserLogin player = userRepository.findByUserName(name);
	   if (player != null){
	  
		   System.out.println("Yay");
	   }
	   else
	   {
		   System.out.println("Nay");
	   }
	
	  
	   ArrayList<Inbox> inboxs = (ArrayList<Inbox>) iR.findByReceiverNameAndStatus(email,"Pending");
	   
	   model.addAttribute("inboxs", inboxs);
	 
	   }
	   
	   return "inbox";
   }
 
   @RequestMapping(value="/showplayers", method=RequestMethod.GET)
   public String players(Model model)
   {
	   String type = "Player";	  
	 
	   ArrayList<UserLogin> players = (ArrayList<UserLogin>) userRepository.findByUserType(type);
	   
//	   List<UserLogin> players = (List<UserLogin>) userRepository.findByUserType("Player");
	   model.addAttribute("players", players);
	  
	   return "players";
	   
   }

   @RequestMapping(value="/showplayers2", method=RequestMethod.GET)
   public String aYeh2()
   {

	   return "players2";

   }
   @RequestMapping(value="/searchP", method=RequestMethod.GET)
   @ResponseBody
   public List<UserLogin> getResource2()
   {
	   String type = "Player";	  
	 
	   ArrayList<UserLogin> cueList = userRepository.findByUserType(type);	   
//	   System.out.print("*********" + cueList.size());

//	   List<UserLogin> cueList = cueList1.Cast<UserLogin>().ToList();
//	   responseGenerator.setJSONData(data.toMap());
	    return cueList;
	   
   }
  
//   @RequestMapping(value="/all", method=RequestMethod.GET)
//   @ResponseBody
//	public List<Team> getResource() {
//		
//	   List<Team> cueList = teamRepository.findAll();
//	   
//	   
//		return cueList;
//	}
   // This method works for deleting players!!!! See players.html for corresponding code, note that both post and get feature in this method
   @RequestMapping(value="/deleteplayer/{id}", method={RequestMethod.POST, RequestMethod.GET})
   public String deleteUser(@PathVariable Long id) {
       
	   UserLogin userLogin = userRepository.findOne(id);
       

       userRepository.delete(userLogin);
       return "redirect:/showplayers";
   }


   @RequestMapping(value="/leaveteam/{id}", method={RequestMethod.POST, RequestMethod.GET})
   public String leaveTeam(@PathVariable Long id) {
	   
	   System.out.println("PLEASE WORK?");
	   
	   Team team = teamRepository.findOne(id);
       
	   Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	   String email = loggedInUser.getName();
	   
	   for(UserLogin ul : team.getUserLogins())
		  {
		   
		   if (ul.getUserName().equals(email))
		   {
			   team.removeUserLogin(ul);
			   ul.setPosition("False");
			   userRepository.save(ul);
			   teamRepository.save(team);
			   break;
		   }
		   
		  }
       return "welcome";
   }

	  
	  @RequestMapping(value="/parsegames", method=RequestMethod.GET)
		public String index(Game game) {
			return "parseGame";
			
		}
	  
	  
	  @RequestMapping(value = "/parsegames", method = RequestMethod.POST)
			public String addNewPost(@Valid Model model) throws IOException {
				
			  Document doc = Jsoup.connect("http://www.irishrugby.ie/club/ulsterbankleagueandcup/fixtures.php").get();
			  

			  
			  Elements kelime = doc.select("tr[id^=fixturerow]");
			    for(Element sectd:kelime){
			        Elements tds = sectd.select("td"); 

			              String result = tds.get(0).text();
			               String result1 = tds.get(1).text();
			               String result2 = tds.get(2).text();
			               String result3 = tds.get(3).text();
			               String result4 = tds.get(4).text();
			               String result5 = tds.get(5).text();
			               String result6 = tds.get(6).text();
			               String result7 = tds.get(7).text();
			             
			          
			               //creates a new game object
			               Game game = new Game();
			               
			            
			               game.setDatePlayed(result);
			               game.setFinalScore(result4);
			               
			               //searches db for team with the name then adds the team to the game homeside
			               Team team = teamRepository.findByTeamName(result3);
			               
			            	
			             
			               game.setHomeSide(team);
			               
			               
			               Team team2 = teamRepository.findByTeamName(result5);
			               game.setAwaySide(team2);

			               //saves full game info to db
			               gameRepository.save(game);	
			               
			              

			          	League league = leagueRepository.findByDivision(result2);
			        
			   

			          	 league.addTeam(team);
			          	 league.addTeam(team2);
			          	 
			          	 league.addGame(game);
			          	 leagueRepository.save(league);
			    
			          	 
			    
			          	 


			               //need to have existing team names in db when running this otherwise ids = null in db, as well as league Div1a in existence
			          	 //after creating the above you can parse and league_games/teams are populated as well as game
			               
			          

				
			    }
			    
			    return "parseresult";
				
			}
	  
	  @RequestMapping(value="/registerteam", method=RequestMethod.GET)
		public String index(Team team) {
			return "teamindex";
			
		}
	  
//	
	
	
		//testing out ajax on registering a team
		
		
		@RequestMapping(value = "/team", method = RequestMethod.POST)
		public String addNewPost(@Valid Team team, Model model) {
			
	
			teamRepository.save(team);
			
			model.addAttribute("teamName", team.getTeamName());
//			model.addAttribute("password", userlogin.getPassword());
		
			return "teamresult";
			
		}
	
		@RequestMapping(value="/registerleague", method=RequestMethod.GET)
		public String index2(Model model) {
		    
			model.addAttribute("league", new League()); //add model to view

		    return "leagueIndex";

		}
	
		////
		@RequestMapping(value = "/registerleague", method = RequestMethod.POST)
		public String addNewLeague(@Valid League league, Model model, BindingResult errors) {
		
		 

			//this is code for a new user
		
			leagueRepository.save(league);
			

	
			return "parseGame";
			
		}
		
		
		//// for updating a player of userlogin, make a new entity with one to one for userlogin called player info, create the userlogin object and associate it with that userlogin 
		
		
	
		//stack overflow get suggestion??
		//link:
		//https://stackoverflow.com/questions/48564093/updating-an-entity-via-thymeleaf-and-spring-mvc
//		public String display(Model model) {
//		    final PlayerPositionFormregleForm playerPositionForm = new playerPositionForm();
//
//		    model.addAttribute("playerPositionForm ", playerPositionForm ); return "view";
//		}
	

		//so far this get is ok and returns the right page
	
		@RequestMapping(value="/updatePlayer", method={RequestMethod.GET})
		public String index3(Model model) {
			
			SecurityContextHolder.getContext().setAuthentication(null);
			model.addAttribute("PlayerInfo", new PlayerInfo()); //add model to view
			
			 return "result";
		}

		
		@RequestMapping(value="/updatePlayer", method=RequestMethod.POST)
		public String addNewPost2(@ModelAttribute("username") String username, @Valid PlayerInfo playerinfo, Model model, BindingResult errors) {
			
		UserLogin user = userRepository.findByUserName(username);
		

		
		user.setPlayerinfo(playerinfo);
		
		userRepository.save(user);
		
		SecurityContextHolder.getContext().setAuthentication(null);
		
	
		return "login";	
		}
	
		@RequestMapping(value="/registeruser", method=RequestMethod.GET)
		public String index(Model model) {
			SecurityContextHolder.getContext().setAuthentication(null);
			
			model.addAttribute("user", new UserLogin()); //add model to view

			 return "index";

		}

	//BELOW ARE MANAGERS INBOX/////
	@RequestMapping(value="/shownotifications", method=RequestMethod.GET)
	public String ManagersInbox(Model model)
	{

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

	      UserLogin user = userRepository.findByUserName(email);

	      
	      //only shows pending messages.
	      ArrayList<Inbox> inboxs = (ArrayList<Inbox>) iR.findByReceiverNameAndStatus(email, "Pending");

		  
	      model.addAttribute("inboxs", inboxs);
		  
		 
	
		return "inbox";
	}
	
	@RequestMapping(value="/showallmessages", method=RequestMethod.GET)
	public String ManagersInbox2(Model model)
	{

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

//	      UserLogin user = userRepository.findByUserName(email);

	      
	      //only shows pending messages.
	      ArrayList<Inbox> inboxs = (ArrayList<Inbox>) iR.findByReceiverName(email);

		  
	      model.addAttribute("inboxs", inboxs);
		  
		
		
		return "inbox";
	}
	
	@RequestMapping(value="/showallOutboxmessages", method=RequestMethod.GET)
	public String PlayersOutbox2(Model model)
	{

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

//	      UserLogin user = userRepository.findByUserName(email);

	      
	      //only shows pending messages.
	      ArrayList<Outbox> outboxs = (ArrayList<Outbox>) oR.findBySenderName(email);

		  
	      model.addAttribute("outboxs", outboxs);
		  
		
		
		return "outbox2";
	}

	//////BELOW ARE PLAYERS OUTBOX
	@RequestMapping(value="/playersOutbox", method=RequestMethod.GET)
	public String PlayersOutbox(Model model)
	{

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

	      UserLogin user = userRepository.findByUserName(email);
	      String status = user.getPosition();
	      
	      //only shows pending messages.
	      ArrayList<Outbox> outbox = (ArrayList<Outbox>) oR.findBySenderName(email);

		  
	      model.addAttribute("outbox", outbox);
	      
	      
	      if (status.equals("True"))
	      {
	    	  status = "No";
	    	  model.addAttribute("status", status);
	      }
	      else
	      {
	    	  status = "Yes";
	    	  model.addAttribute("status", status);
	      }
	     
		  
		 
	
		return "outbox";
	}
	
	   @RequestMapping(value="/viewManager/{name:.+}", method=RequestMethod.GET)
	   public String ViewManager(Model model, @PathVariable String name) throws NonUniqueResultException {
		  

		   //this method works, although returns "null" if theres duplicate users
		   //wont work for ***@gmail.com address's (FIXED: truncating the name, see above {name:.+})
		   //Going to attempt to change the status of the notification to seen once a view player is clicked
		   Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

		   
		   
		   Outbox outbox = oR.findBySenderNameAndReceiverName(email, name);
		   outbox.setViewed("Seen");
		   ///
		   oR.save(outbox);
		   
		   
		   UserLogin player = userRepository.findByUserName(name);
		   if (player != null){
		   model.addAttribute("player", player);
		   }
		   else
		   {
		   System.out.println("Player is null");
		   }
		   return "singlemanager";
	   }
	
	   @RequestMapping(value="/approveAndJoin/{name:.+}", method=RequestMethod.GET)
	   public String ApproveAndJoin(Model model, @PathVariable String name) throws NonUniqueResultException {
		  

		   System.out.println(name);
		   
		  
		   
		   //this method works, although returns "null" if theres duplicate users
		   //wont work for ***@gmail.com address's (FIXED: truncating the name, see above {name:.+})
		   //Going to attempt to change the status of the notification to seen once a view player is clicked
		   Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		   String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

		   
		   
		   Outbox outbox = oR.findBySenderNameAndReceiverName(email, name);
		   outbox.setStatus("Approved");
		   oR.save(outbox);
		   
		   Inbox inbox = iR.findBySenderNameAndReceiverName(email, name);
		   inbox.setStatus("Approved");
		   iR.save(inbox);
		   
		   
		   
		   UserLogin player = userRepository.findByUserName(email);
		  
		   ArrayList<Team> teams = (ArrayList<Team>) teamRepository.findAll();
		   
			  for (Team t : teams)
			  {
				  for(UserLogin ul : t.getUserLogins())
				  {
					  if(ul.getUserName().equals(name) && ul.getUserType().equals("Manager"))
					  {
					    
						ok = true;
					    t.addUserLogin(player);
					     teamRepository.save(t);
						break;
					  }
				  }
			  }
			  if (ok == true)
			  {
				  player.setPosition("True");
				  userRepository.save(player);
			  
			  }
		   
		
		   return "welcome";
	   }

//	   @RequestMapping(value="/registeruser", method=RequestMethod.GET)
//		public String index(Model model) {
//			SecurityContextHolder.getContext().setAuthentication(null);
//			
//			model.addAttribute("user", new UserLogin()); //add model to view
//
//			 return "index";
//
//		}

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public String addNewPost(@Valid UserLogin user, Model model, BindingResult errors) {
		
		//error handling code goes here?
		
		Role role1 = new Role();
		Role role2 = new Role();
		
		role1.setRole("USER");
		role2.setRole("ADMIN");
		

	   String result = null;
	   
	   
	   user.setUserStatus(true);
	 
		//userRepository.save(user);
		
		model.addAttribute("username", user.getUserName());
		model.addAttribute("PlayerInfo", new PlayerInfo()); 
		if (user.getUserType().equals("Player"))
		{
			
		
//			Role roley = roleRepository.findOne(userrole);
			
			user.addRole(role1);
			user.setPosition("False");

			userRepository.save(user);		

			result = ("result");
			
			//do whats mentioned below for all the entities
			//players result page should ask the player more questions (see the player model for these attributes, then save them to this "player" and point the player to the welcome page
		}

		if (user.getUserType().equals("Manager"))
		{
	
//            Role roley = roleRepository.findOne(role2);
			
			user.addRole(role2);
			user.setPosition("False");

			userRepository.save(user);	
			
			//here i will try and add a user role (admin) to the manager entity so they can login as admin
			result = ("managerResult");
		}

		if (user.getUserType().equals("Physio"))
			
		{
	
			user.addRole(role1);
			user.setPosition("False");

			userRepository.save(user);	
			
			result = ("physioResult");
		}
		if (user.getUserType().equals("CoachingStaff"))
		{
			
//       Role roley = roleRepository.findOne(userrole);
			
			user.addRole(role1);
			user.setPosition("False");
			userRepository.save(user);	
			
			result = ("coachingStaffResult");
		}
	
	
	
		return result;
		
	}
	
	@RequestMapping(value="/freePositions", method=RequestMethod.GET)
	public String PlayersPositions(Model model)
	{
		 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); // getName() is springs way to get the logged in user name, which in my case is their email (i.e what they login with)

	      UserLogin user = userRepository.findByUserName(email);
	      
	      String position = user.getPlayerinfo().getPosition();
	      
	      
	     
	      
//		int forwards = 13;
//		int backs = 10;
		
//		int frontRow = 6;
//		int locks = 3;
//		int backrow = 4;
//		int halfBacks=3;
//		int centres=4;
//		int backThree=5;
		
		int wingers = 3;
		int fullback = 1;
		int centres = 3;
		int outhalf = 1;
		int scrumhalf =1;
		int props = 4;
		int hooker = 1;
		int secondRow = 3;
		int backrow = 5;
//		int fullback = -1;
//		int centres = -3;
//		int outhalf = -1;
//		int scrumhalf =-1;
//		int props = -4;
//		int hooker = -1;
//		int secondRow = -3;
//		int backrow = -5;
		

		ArrayList<Team> matchedTeams = new ArrayList<Team>();
			
		ArrayList<Team> teams = (ArrayList<Team>) teamRepository.findAll();
		   
		  for (Team t : teams)
		  {
			  
			  /// Going through each team
			  
			  for(UserLogin ul : t.getUserLogins())
			  {
				  if(ul.getPlayerinfo().getPosition().equals("RightWing"))
				  {
				    wingers--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("LeftWing"))
				  {
				    wingers--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("FullBack"))
				  {
				    fullback--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("OutsideCentre"))
				  {
				    centres--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("InsideCentre"))
				  {
				    centres--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("OutHalf"))
				  {
				   outhalf--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("ScrumHalf"))
				  {
				    scrumhalf--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("Eight"))
				  {
				    backrow--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("OpenSideFlanker"))
				  {
				    backrow--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("BlindSideFlanker"))
				  {
				    backrow--;
				  } 
				  if(ul.getPlayerinfo().getPosition().equals("SecondRow"))  
				  {
					secondRow--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("LooseHead"))  
				  {
					props--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("Hooker"))  
				  {
					hooker--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("TightHead"))  
				  {
					props--;
				  }
				  
			  }//end of loop searching through players on a team
			  
			  if (  fullback >= 0 && position.equals("FullBack") )
			  {
				  
				  matchedTeams.add(t);
			  }
			  if (wingers >= 0 && position.equals("RightWing")  || position.equals("LeftWing"))
			  {
				  System.out.println("TTTTTTTTTTTTTTTTTTTTTEST TEAM : " + wingers + " " + position);
				  matchedTeams.add(t);
			  }
			  if (  centres >= 0 && (position.equals("InsideCentre") || position.equals("OutsideCentre") ))
			  {
				  matchedTeams.add(t);
			  }
			  if (  outhalf >= 0 && position.equals("OutHalf") )
			  {
				  matchedTeams.add(t);
			  }
			  if (  scrumhalf >= 0 && position.equals("ScrumHalf") )
			  {
				  matchedTeams.add(t);
			  }
			  if (  backrow >= 0 && (position.equals("Eight") || position.equals("BlindSideFlanker") || position.equals("OpenSideFlanker") ))
			  {
				  matchedTeams.add(t);
			  }
			  if ( secondRow >= 0 && position.equals("SecondRow") )
			  {
				  matchedTeams.add(t);
			  }
			  if (  props >= 0 && (position.equals("TightHead") || position.equals("LooseHead"))  )
			  {
				  matchedTeams.add(t);
			  }
			  if (  hooker >= 0 && position.equals("Hooker") )
			  {
				  matchedTeams.add(t);
			  }  
			/// Going through each team
			  
		  }
		 
		  
		  ////////////////////Codes getting to here at least.

	
		  
	      model.addAttribute("teams", matchedTeams);
		  

		return "teams";
	}
	
	@RequestMapping(value="/viewpositioninfo", method=RequestMethod.GET)
	public String ManagersTeamPositions(Model model)
	{
		int wingers = 3;
		int fullback = 1;
		int centres = 3;
		int outhalf = 1;
		int scrumhalf =1;
		int props = 4;
		int hooker = 1;
		int secondRow = 3;
		int backrow = 5;
		
		
		 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	      String email = loggedInUser.getName(); 

	      UserLogin user = userRepository.findByUserName(email);
	      String teamName = "";
	      
	      /////////////////FINDS THE TEAM
	      ArrayList<Team> teams = (ArrayList<Team>) teamRepository.findAll();
		   
		  for (Team t : teams)
		  {
			  for(UserLogin ul : t.getUserLogins())
			  {
				  if(ul.getUserName().equals(email))
				  {
				     teamName = t.getTeamName();
				     break;

				  }
			  }
		  }
		  
		  System.out.println("T: " + teamName);
	      ////////////////////////////////// Works
		  
		  
		  //Managers Team
		  Team team = teamRepository.findByTeamName(teamName);
		  
		  //ArrayList For Players she needs
		  ArrayList<UserLogin> players =  new ArrayList<UserLogin>();

		  
		  //Checking all teams users positions and subtracting the position variables accordingly
		   for(UserLogin ul : team.getUserLogins())
			  {
			   
			   if (ul.getUserType().equals("Player"))
				   
			   {
			   
				  if(ul.getPlayerinfo().getPosition().equals("RightWing"))
				  {
				    wingers--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("LeftWing"))
				  {
				    wingers--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("FullBack"))
				  {
				    fullback--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("OutsideCentre"))
				  {
				    centres--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("InsideCentre"))
				  {
				    centres--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("OutHalf"))
				  {
				   outhalf--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("ScrumHalf"))
				  {
				    scrumhalf--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("Eight"))
				  {
				    backrow--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("OpenSideFlanker"))
				  {
				    backrow--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("BlindSideFlanker"))
				  {
				    backrow--;
				  } 
				  if(ul.getPlayerinfo().getPosition().equals("SecondRow"))  
				  {
					secondRow--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("LooseHead"))  
				  {
					props--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("Hooker"))  
				  {
					hooker--;
				  }
				  if(ul.getPlayerinfo().getPosition().equals("TightHead"))  
				  {
					props--;
				  }
				  
			   }
			  }/// End of loop recording the players positions etc.

		   	  //idea behind below , if the position needs to be filled, pass the variable through model attribute, display above a search players index with players.playerinfo.getpositions showing
			  if (  fullback >= 0 ) //&& position.equals("FullBack")
			  {
				 int recomAmountFB = 1; 
				 String fullback2 = "FullBack";
				 model.addAttribute("fullback", fullback2);
				 model.addAttribute("fullbackNo", fullback);
				 model.addAttribute("recomAmountFB", recomAmountFB);
				 
				 
			  }
			  if (wingers >= 0) //&& position.equals("RightWing")  || position.equals("LeftWing")
			  {
				  int recomAmountW = 3;
				  String winger2 = "Wingers";
				  model.addAttribute("winger", winger2);
				  model.addAttribute("wingerNo", wingers);
				  model.addAttribute("recomAmountW", recomAmountW);


			  }
			  if (  centres >= 0)// && (position.equals("InsideCentre") || position.equals("OutsideCentre") ))
			  {
				  int recomAmountC = 3;
				  String centres2 = "Centres";
				  model.addAttribute("centre", centres2);
				  model.addAttribute("centreNo", centres);
				  model.addAttribute("recomAmountC", recomAmountC);


			  }
			  if (  outhalf >= 0 )//&& position.equals("OutHalf") )
			  {
				  int recomAmountO = 1;
				  String outhalf2 = "OutHalf";
				  model.addAttribute("outhalf", outhalf2);
				  model.addAttribute("outhalfNo", outhalf);
				  model.addAttribute("recomAmountO", recomAmountO);

			  }
			  if (  scrumhalf >= 0)// && position.equals("ScrumHalf") )
			  {
				  int recomAmountS = 1;
				  String scrumhalf2 = "ScrumHalf";
				  model.addAttribute("scrumhalf", scrumhalf2);
				  model.addAttribute("scrumhalfNo", scrumhalf);
				  model.addAttribute("recomAmountS", recomAmountS);


			  }
			  if (  backrow >= 0) // && (position.equals("Eight") || position.equals("BlindSideFlanker") || position.equals("OpenSideFlanker") ))
			  {
				  int recomAmountB = 5;
				  String backrow2 = "Backrows";
				  model.addAttribute("backrow", backrow2);
				  model.addAttribute("backrowNo", outhalf);
				  model.addAttribute("recomAmountB", recomAmountB);


			  }
			  if ( secondRow >= 0)// && position.equals("SecondRow") )
			  {
				  int recomAmountSR = 3;
				  String secondrow2 = "SecondRow";
				  model.addAttribute("secondrow", secondrow2);
				  model.addAttribute("secondrowNo", secondRow);
				  model.addAttribute("recomAmountSR", recomAmountSR);


			  }
			  if (  props >= 0 )// && (position.equals("TightHead") || position.equals("LooseHead"))  )
			  {
				  int recomAmountP = 4;
				  String prop2 = "Props";
				  model.addAttribute("prop", prop2);
				  model.addAttribute("propNo", props);
				  model.addAttribute("recomAmountP", recomAmountP);


			  }
			  if (  hooker >= 0)// && position.equals("Hooker") )
			  {
				  int recomAmountH = 1;
				  String hooker2 = "Hooker";
				  model.addAttribute("hooker", hooker2);
				  model.addAttribute("hookerNo", hooker);
				  model.addAttribute("recomAmountH", recomAmountH);


			  }  


		return "neededPlayers";
	}
	

}
