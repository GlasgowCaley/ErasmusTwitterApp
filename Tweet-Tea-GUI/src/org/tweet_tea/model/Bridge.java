package org.tweet_tea.model;

public class Bridge {
	public Bridge(){
		super();		
		System.out.println("Bridge init");	
		
	}
	public void print( String text ){
		System.out.println(text);
	}
	
	public boolean retweet(String tweetId){
		System.out.println("Retweet : "+tweetId);
		boolean result = true;
		try {
			TwitterAPI.retweet(tweetId);
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
}
