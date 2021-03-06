package org.tweet_tea.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Stack;


import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import netscape.javascript.JSObject;

import org.tweet_tea.Json.JsonWrapper;
import org.tweet_tea.model.*;
import org.w3c.dom.Document;
import javafx.concurrent.Worker.State;


//TODO : add addFirst() to automaticaly inject new tweets (whithout refreshing manualy)


/**
 * Is the controler of the center area, where we see tweets
 * @author Geoffrey
 * @see the word document why we use a webview instead of JavaFx components
 *
 */
public class TweetSectionGenerator {

	
	/**
	 * The webview to manage
	 */
	WebView view;
	/**
	 * The webengine associated
	 */
	WebEngine engine;
	
	/**
	 * CSS of the view
	 */
	private String css;
	
	/**
	 * JQuery
	 */
	private String jQuery;
	
	/**
	 * Main.js
	 */	
	private String mainJs;
	
	/**
	 * The google JSON parser
	 */
	Gson serializer ;
	
	/**
	 * The window object in javascript
	 */
	JSObject window;

	
	public TweetSectionGenerator(){
		
		serializer = new Gson();

		view = new WebView();
		engine = view.getEngine();
		
		view.setContextMenuEnabled(false);
		view.setCache(true);
		
		
		
		String html = "file://" +getClass().getClassLoader().getSystemClassLoader().getResource(".").getPath() +"main.html";
		engine.load(html);
				
		System.out.println( html );
		
		
		
		engine.getLoadWorker().stateProperty().addListener(
		        new ChangeListener<State>() {          

					
					public void changed(ObservableValue<? extends State> arg0,
							State arg1, State newState) {
						// TODO Auto-generated method stub
						if ( newState == State.SUCCEEDED){							
					      
							window = (JSObject) engine.executeScript("window");
							window.setMember("java", new Bridge());
							window.eval("upcall('hello');");
						}
					}
		        });
	}
		
	
	
	/**
	 * Add a tweet to the webview ( but don't show it )
	 * @param tweet
	 */
	public void addTweet(Tweet tweet){
				
		String json = serializer.toJson(new JsonWrapper(tweet));			// beware to the JsonWrapper !!
		addBySendingToJS(json);   // we send the tweet to Javascript
	}
	
	/**
	 * Clear all the tweets in webview but keep the page loaded and the scripts lanched
	 * This is better than to regenerate new webview
	 */
	public void clear(){
		engine.executeScript("clearAll()");
	}
	
	
	

	/**
	 * Send a JSON tweet to JS. 
	 * @param json
	 */
	private void addBySendingToJS(String json){
		
		//json= json.replace("\\", "\\\\");
		// we need to translate some chars in HTML entities
		json = json.replace("'", "&apos;'").replace("\\\"", "&quot;");  // replace ' by \' and " by \" jQuery will automaticaly replace all on the JS side
		json = json.replace("\n", "<br/>");
		json = json.replace("\\n", "<br/>");
		//System.out.println(json);
	
		try{
			engine.executeScript("add('"+json+"')");
		}
		catch(Exception e){
			System.out.println("Error while injecting in webView : " +e.getMessage());
		}
		
		
	}
	
	/**
	 * Ask the JS to show tweets
	 * Useful because you can show the tweets after they are loaded
	 * If you show the tweet as the same time as you inject it , the webview slows
	 */
	public void showTweets(){
		engine.executeScript("showTweets()");
	}

	
	/**
	 * Equeivalent of PHP get_file_content();
	 * Read a text file into a String
	 * @param filePath 
	 * @return	String
	 * @throws IOException
	 */
	private String readFileAsString(String filePath) throws IOException {
	
       StringBuffer buffer = new StringBuffer();
       String line;
		try{
			BufferedReader fich = new BufferedReader(new FileReader(filePath));
			
			try{
				while((line = fich.readLine())!=null){
			    	   buffer.append(line);
			    	  
			       }
			}
			finally{
				fich.close();
			}
			
		       
		}
		catch(Exception e){
			throw new IOException("Tweets's CSS not found/loaded: "+e.getMessage());
		}
		
       return buffer.toString();
    }
	
//	public boolean isReady(){
//		
//		boolean isready= false;
//		Worker worker = engine.getLoadWorker();
//		if(worker.getState()==Worker.State.SUCCEEDED)		return true;
//		else return false;
//		
//		// ok ok c'est pas propre mais au moins c'est pas ambigu�
//		
//		
//	}
	
	
	/**
	 * Append text ( Not Tweet ) to the webview. Be sure it's JSON , else > JSException or worth : nothing
	 * @param text
	 */
	public void appendText(String text){
		addBySendingToJS(text);
	}
	
	
	
	/**
	 * Getters
	 * 
	 */
	
	public WebView  getWebView(){
		return view;
	}
	
	
	
	
	
}
