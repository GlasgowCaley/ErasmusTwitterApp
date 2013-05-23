package src.org.tweet_tea.model;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import src.org.tweet_tea.gui.User_Popup;

public class Bridge {
	public Bridge(){
		super();		
		
	}
	public void print( String text ){
		System.out.println(text);
	}
	
	public String retweet(String tweetId){
		System.out.println("Retweet : "+tweetId);
		
		String result = null;
		try {
			result = TwitterAPI.retweet(tweetId);
			System.out.println(result);
		} catch (Exception e) {
			// nothing to do here, javascript will check if result is null or not
		}
		return result;
	}
	public boolean deleteReTweet(String tweetId){
		System.out.println("Delete Retweet : "+tweetId);
		
		boolean result = true;
		try {
			TwitterAPI.delete_tweet(tweetId);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	public boolean addFavorite(String tweetId){
		System.out.println("AddFavorite : "+tweetId);
		boolean result = true;
		try {
			TwitterAPI.setFavorite(tweetId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = false;
		}
		return result;
	}
	
	public boolean follow(String screen_name){
		boolean result = true;
		
		try{
			TwitterAPI.follow(screen_name);
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public void createUserPopup(String screenName){
		try {
			
			User u = TwitterAPI.getUserByScreenName(screenName);
			User_Popup up = new User_Popup(u);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean respondTweet(String id, String message){
		boolean res = true;
		
		try {
			TwitterAPI.respondTweet(id, message);
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}
		
		return res;
	}
	
	public void browseUrl ( String href ){
		if(Desktop.isDesktopSupported())
		{
			
			 try {
				
				Desktop.getDesktop().browse(new URI(href));		// open a webpage in the default browser
			} catch (IOException e) {
				// TODO make something better - GG - FR
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO make something better - GG - FR
				e.printStackTrace();
			}
		}
	}
	
}
