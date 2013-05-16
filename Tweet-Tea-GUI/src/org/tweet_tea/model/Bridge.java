package org.tweet_tea.model;

public class Bridge {
	public Bridge(){
		super();		
		System.out.println("Bridge init");	
		
	}
	public void print( String text ){
		System.out.println(text);
	}
	
	public String retweet(String tweetId){
		System.out.println("Retweet : "+tweetId);
		
		String result = null;
		try {
			result = TwitterAPI.retweet(tweetId);
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
		System.out.println("Retweet : "+tweetId);
		boolean result = true;
		try {
			TwitterAPI.setFavorite(tweetId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = false;
		}
		return result;
	}
	
}
