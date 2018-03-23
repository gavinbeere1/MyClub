package com.FYP.Club.Scraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class Scrape {
 
  public static void main(String[] args) throws IOException {
//    try {
      // fetch the document over HTTP
      Document doc = Jsoup.connect("https://www.google.ie/?gws_rd=cr&dcr=0&ei=ShUgWtmzF6LOgAao5a_wCA").get();
      
      // get the page title
      String title = doc.title();
      System.out.println("title: " + title);
      
      // get all links in page
      Elements links = doc.select("#_eEe");

      System.out.println("text: " + links.text());
      

  }
}