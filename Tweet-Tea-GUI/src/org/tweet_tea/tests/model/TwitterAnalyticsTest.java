/**
 * 
 */
package org.tweet_tea.tests.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import org.tweet_tea.model.Tweet;
import org.tweet_tea.model.User;
import org.tweet_tea.model.TwitterAnalytics;

/**
 * @author jim
 *
 */
public class TwitterAnalyticsTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * Test method for {@link org.tweet_tea.model.TwitterAnalytics#distinctUserNames(org.tweet_tea.model.Tweet[])}.
	 */
	@Test
	public void testDistinctUserNames()
	{	
		// object under test
		TwitterAnalytics analytics = new TwitterAnalytics();
		
		// arrange
		Tweet[] testTweets = new Tweet[4];		
		testTweets[0] = new Tweet(new User("user1"));
		testTweets[1] = new Tweet(new User("user2"));
		testTweets[2] = new Tweet(new User("user2"));
		testTweets[3] = new Tweet(new User("user3"));
		
		// act
		String[] distinctUserNames = analytics.distinctUserNames(testTweets);
		int numberOfDistinctUserNamesFound = distinctUserNames.length;
		
		// assert
		assertEquals("should be 3 distinct names", 3, numberOfDistinctUserNamesFound);
	}

}
