package org.tweet_tea.model;

public class TopHashtags {
	String Hashtag;
	int count;
	
	TopHashtags(String text){
		Hashtag=text;
		count++;
	}
	
	public String getTHName(){
		return Hashtag;
	}
	
	public int getHTCount(){
		return count;
	}

}
