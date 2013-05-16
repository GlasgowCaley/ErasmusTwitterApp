package org.tweet_tea.Json;

import org.tweet_tea.model.Tweet;
import org.tweet_tea.model.User;
import org.tweet_tea.resources.Res;

/**
 * 
 * Give a good structure to parse a Tweet to JSON.
 * All essential info needed in a tweet need to be here
 * the rest is just useless
 * @author Geoffrey
 *
 */
public class JsonWrapper {

	String id;
	String name;
	String twitterPrefix;
	String screen_name;
	String pictureUrl;
	String text;
	String created_at;
	
	//TODO: add date, entities etc ...
	


	public  JsonWrapper(Tweet t) {
		
		twitterPrefix = Res.twitterPrefix;
		id= t.getID();
		text = t.getMessage();
		User user;
		if( (user= t.getUser())!=null ){
			name = user.getName();
			screen_name = user.getScreenName();
			pictureUrl = user.getProfileImageURL();
		}
		else{
			name = t.getFromUserName();
			screen_name = t.getFromUser();
			pictureUrl = t.getProfileImageURL();	
		}		
	}

}
