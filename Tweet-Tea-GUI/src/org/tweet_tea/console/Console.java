package org.tweet_tea.console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.tweet_tea.model.DirectMessage;
import org.tweet_tea.model.TopHashtags;
import org.tweet_tea.model.Tweet;
import org.tweet_tea.model.TwitterAPI;
import org.tweet_tea.model.TwitterAnalytics;
import org.tweet_tea.model.User;


/**
 * Console interface for Tweet'Tea
 * @author Geoffrey
 *
 */
public class Console {

	
	// TODO : Change all strings by a constant in Res
	
	
	public static void main(String[] args) throws Exception {
		
		Scanner scanner = new Scanner(System.in);
		String scanned;		// declared here because multiple declaration aren't allowed in swicth/case
		
		
		try {
			TwitterAPI.loadAuthToken();
			
			print("Auth token loaded successfully");
		} catch (Exception e2) {
			
			print("/!\\ Auth token not loaded" +e2.getMessage());
		}

			
		Tweet[] tweets = null ;
		List<TopHashtags> hashlist = null;
		
		while(true){
			
			int choice = menu();
			
			switch (choice) {
			case 1:
				
				try {
					tweets = TwitterAPI.getHomeTimeline();
					
					if(tweets!=null){
						for(Tweet tweet : tweets){	print(tweet.toString());	}
					}
					
					sleep(1000);
					
				} catch (Exception e1) {
					e1.printStackTrace();	// is usefull for the moment 
					print("Need to be authenticated, authenticate first");
				}
				
				break;
			case 2:
				
				print("Enter screen name : ");
				scanned = scanner.nextLine();
				
				try {
					tweets = TwitterAPI.getScreen(scanned);
					
					if(tweets!=null){
						for(Tweet tweet : tweets){	print(tweet.toString());	}
					}
					
				} catch (Exception e1) {
					
					print("Screen not found");
				}
				
				break;
				
			case 3:
				print("Enter tag to search : ");
				scanned = scanner.nextLine();
				
				tweets = TwitterAPI.search(scanned);
				if(tweets!=null){
					for(Tweet tweet : tweets){	 print( tweet.toString());	}
				}
				break;
			case 4:
				
				print("");
				print("What's new ?");
				
				scanned = scanner.nextLine();
				
				TwitterAPI.send_tweet(scanned);
				
				break;
				
			case 5:
				
					User user = TwitterAPI.getMyUserInfo();
					print(user.toString());
				
				break;
				
			case 6:
				
					print("Enter new description");
					scanned = scanner.nextLine();
					User updated_user= TwitterAPI.updateDesc( scanned );
					print("Desc updated : ");
					print(updated_user.toString());
				
				break;
			case 7:
				
				try {
					TwitterAPI.firstauth();
				} catch (Exception e) {
					print(e.getMessage());
				}
				
				break;
				
			case 8:
				String t, mess;
				
				System.out.println("For who ? Enter his screenName");
				t = scanner.nextLine();
				System.out.println("Your Message ?");
				mess = scanner.nextLine();
				
				try{
					TwitterAPI.sendMessage(mess, t);
					
					System.out.println("\nSuccess\n");
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
				
			case 9:
				DirectMessage[] list = TwitterAPI.getRecivedPrivateMessages(1);
				if(list != null){
					for (DirectMessage directMessage : list) {
						System.out.println(directMessage);
					}
				}
				else{
					System.out.println("No messages to show");
				}
				
				
				break;
			case 10:
				DirectMessage[] list2 = TwitterAPI.getSentPrivateMessages(1);
				if(list2 != null){
					for (DirectMessage directMessage : list2) {
						System.out.println(directMessage);
					}
				}
				else{
					System.out.println("No messages to show");
				} 
				
				
				break;
			
			case 11:
				System.out.println("Enter a Tweet id : ");
				String id = scanner.nextLine();
				try{
					TwitterAPI.retweet(id);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
				
			case 12:
				System.out.println("Enter a screen name : ");
				String followed = scanner.nextLine();
				try{
					TwitterAPI.follow(followed);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
				break;
				
			case 13:
				System.out.println("Enter the tweet ID: ");
				try{
					TwitterAPI.setFavorite(scanner.nextLine());
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
				
			case 14:
				System.out.println("Enter the tweet ID: ");
				try{
					TwitterAPI.removeFavorite(scanner.nextLine());
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
				
			case 15:
				System.out.println("Enter the tweet ID: ");
				try{
					TwitterAPI.delete_tweet(scanner.nextLine());
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
			
			case 16:
				try {
					tweets = TwitterAPI.getScreen(TwitterAPI.getMyUserInfo().getScreenName());
					
					if(tweets!=null){
						for(Tweet tweet : tweets){	print(tweet.toString());	}
					}
					
				} catch (Exception e1) {
					
					print("Screen not found");
				}
				break;
			
			case 17:
				User[] followers;
				String screenName;
				
				System.out.print("Enter Screen Name: ");
				screenName = scanner.nextLine();
				
				followers = TwitterAPI.getFollowers(screenName);
				if(followers!=null){
				for(User follower: followers){
					System.out.println(follower.getScreenName());
				}
				}
				break;
				
			case 18:
				try{
					tweets=TwitterAPI.getScreen(TwitterAPI.getMyUserInfo().getScreenName());
					hashlist = TwitterAnalytics.getTopHashtags(tweets);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				if(hashlist!=null){
					for(TopHashtags temphashlist : hashlist){
						System.out.println("#" + temphashlist.getTHName() + " | Used " + temphashlist.getHTCount() + " time(s)");
					}
				}
				break;
				
			case 19:
				System.out.println("Enter the screen name you want to block: ");
				try{
					TwitterAPI.blockUser(scanner.nextLine());
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
			case 20:
				System.out.println("Enter the screen name you want to unblock: ");
				try{
					TwitterAPI.unblockUser(scanner.nextLine());
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
			case 21:
				try{
					TwitterAPI.logOut();
					TwitterAPI.loadAuthToken();
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
			case 22:
				clear();
				print("Bye!");
				System.exit(0);
				break;
				
				
			default:
				print("Please enter a correct value.");
				sleep(1000);
				clear();
				break;
			}
			
			
			
		}
		

		
	}

	/**
	 * An alias for System.out.println()
	 * @param s
	 */
	private static void print(String s){
		System.out.println(s);
	}
	
	private static int menu(){
		int retour=0;
		
		String menu="Erasmus Twitter'Tea\n"
				   +"\n"
				   +"1- Home\n"
				   +"2- Get tweets by screen name\n"
				   +"3- Search\n"
				   +"4- Post a tweet\n"
				   +"5- My info\n"
				   +"6- Change my description\n"
				   +"7- Auth\n"
				   +"8- Send a private message\n"
				   +"9- Get last received private messages\n"
				   +"10- Get last sent private messages\n"
				   +"11- Retweet with the ID\n"
				   +"12- Follow a new user\n"
				   +"13- Set a new favorite\n"
				   +"14- Remove a tweet from favorites\n"
				   +"15- Delete a tweet\n"
				   +"16- Get own tweets\n"
				   +"17- Show Folowers\n"
				   +"18- Get Most Used Hashtags\n"
				   +"19- Block User\n"
				   +"20- Unblock User\n"
				   +"21- Log Out\n"
				   +"22- Quit";

		print(menu);
		
		Scanner sc = new Scanner(System.in);
		retour = sc.nextInt();
		
		return retour;
		
		
	}
	
	private static void clear (){
		try {
				if(System.getProperty("os.name" ).contains("Windows" ))					
					Runtime.getRuntime().exec("cls");					
				else
				 Runtime.getRuntime().exec("clear");
	
		} catch (IOException e) {
				
			for(int i=0;i<100;i++){
				print(" ");
			}
		}
	}
	
	private static void sleep(int milisecond){
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
						
		}
	}
	
	
}
