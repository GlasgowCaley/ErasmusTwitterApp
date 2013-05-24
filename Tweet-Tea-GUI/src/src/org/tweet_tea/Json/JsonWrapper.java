package src.org.tweet_tea.Json;

import src.org.tweet_tea.resources.Res;

import src.org.tweet_tea.model.Tweet;
import src.org.tweet_tea.model.User;

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
	String retweeted;
	String favorited;
	
	//TODO: add date, entities etc ...
	


	public  JsonWrapper(Tweet t) {
		
		twitterPrefix = Res.twitterPrefix;
		id= t.getID();
		text = t.getMessage();
		created_at = t.getDate();
		retweeted = ""+ t.getRetweeted();
		favorited = ""+ t.getFavorited();
		System.out.print(retweeted+"|"+favorited);
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
