package com.FYP.Club.Scraper;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.FYP.Club.model.Game;
import com.FYP.Club.repository.GameRepository;
 
public class Scrape2 {
	
	@Autowired
    GameRepository gameRepository;
 
  public static void main(String[] args) throws IOException, ParseException {

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
	             

	               System.out.println("Date: " + result);
	               System.out.println("Time: " + result1);
	               System.out.println("League: " + result2);
	               System.out.println("Home Team: " + result3);
	               System.out.println("Score: " + result4);
	               System.out.println("Away Team: " + result5);
	               System.out.println("Venue: " + result6);
	               System.out.println("Ref: " + result7);
	              
//	               String target = "Thu Sep 28 20:29:30 JST 2000";
//	               DateFormat df = new SimpleDateFormat("EEE dd MMM YY", Locale.ENGLISH);
//	               Date change =  df.parse(result);  
//	               System.out.println(result);
	               
              
	               Game game = new Game();
	               
	               game.setDatePlayed(result);
	               game.setFinalScore(result4);
	               game.setAwaySide(null);
	               game.setHomeSide(null);
//	               
//	               gameRepository.add(game);
	               



	                
	    }
      

  }
}
