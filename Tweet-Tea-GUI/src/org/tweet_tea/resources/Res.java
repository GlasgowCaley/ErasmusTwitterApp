package src.org.tweet_tea.resources;
/**
 * Define Resources
 * @author Geoffrey
 *
 */
public class Res {

	
	
	/**
	 * Api's domain
	 */
	public static final String domain="https://api.twitter.com/1/";
	
	public static final String api_key = "ylVhrIQ6Yooukipyz1Qjw";							// comming from your twitter account
	public static final String api_secret = "s8zxt9BNbrynTwccPdkCqUYlngLc36Ta6OPp3RHL3E";
	
	public static final String timeline_prefix = "statuses/user_timeline.json?";
	public static final String home_timeline_prefix= "statuses/home_timeline.json?include_entities=true";
	public static final String search_prefix= "http://search.twitter.com/search.json?include_entities=true";
	public static final String update_prefix = "statuses/update.json";
	public static final String private_message_prefix = "https://api.twitter.com/1.1/direct_messages/new.json?";
	public static final String private_message_feed_received = "https://api.twitter.com/1/direct_messages.json?";
	public static final String private_message_feed_sent = "https://api.twitter.com/1/direct_messages/sent.json?";
	public static int refreshTime = 30;
	public static String user_popup_path = "/User_Popup.fxml";

	/**
	 * Classic Domain
	 */
	
	public static final String twitterPrefix =  "https://twitter.com/";
	
	// Language resources
	
	// TODO : Everything
	
	public static Object lang;
	
	/**
	 * 
	 * @param langCode FR, EN, DE etc...
	 */
	public static void setLang(String langCode){
		
		// No Switch / case here , switch/case on Strings only allowed on java > 1.6
		// default : EN
		if( langCode.equals("FR")){
			Res.lang=new FR();
		}
		else {
			Res.lang=new EN();
		}
		
		
	}
	
	private static class EN{
		public final String title = "TwitterCli";
	}
	
	private static class FR{
		public final String title = "TwitterCli";
	}
}
