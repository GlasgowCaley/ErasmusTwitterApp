/**
 * 
 */
package org.tweet_tea.model;

import java.util.ArrayList;

/**
 * @author jim
 * 
 */
public class TwitterAnalytics
{
	public String[] distinctUserNames(Tweet[] tweets)
	{
		// use ArrayList instead of array as we don't know how many results
		// there will be and want to be able to use contains method
		ArrayList<String> results = new ArrayList<String>();

		for (Tweet tweet : tweets)
		{
			if (!results.contains(tweet.getUser().getName()))
			{
				results.add(tweet.getUser().getName());
			}
		}

		String[] resultArray = new String[results.size()];
		return results.toArray(resultArray);
	}

}
