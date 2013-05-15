package org.tweet_tea.model;

public class Bridge {
	public Bridge(){
		super();		
		System.out.println("Bridge init");	
		
	}
	public void print( String text ){
		System.out.println(text);
	}
	
	public void retweet(String tweetId){
		try {
			TwitterAPI.retweet(tweetId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
