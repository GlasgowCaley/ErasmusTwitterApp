package org.tweet_tea.model;

/**
 * Hashtags from a tweet ( meta info )
 * @author Geoffrey
 *
 */
public class Hashtags {

	private String text;
	/**
	 * begin at index [0] in the text
	 * end at index [1] in the
	 * @see JSON object directly
	 */
	private int[] indices; 
	
	public String getText(){
		return text;
	}
}
