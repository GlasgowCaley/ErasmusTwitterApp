package org.tweet_tea.tests.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tweet_tea.model.TwitterAPI;
import org.tweet_tea.model.Tweet;

public class TwitterAPITest
{

	@Before
	public void setUp() throws Exception
	{
		TwitterAPI.loadAuthToken();
	}

	@Test
	public void testGetHomeTimeline() throws Exception
	{
		Tweet[] tweets = TwitterAPI.getHomeTimeline(20);
		
	   //  assertEquals("Should get 20 tweets", 20, tweets.length);   // not a good test - see https://dev.twitter.com/issues/396
		
		assertTrue("Should get between 1 and 20 tweets", 
				tweets.length >= 1 && tweets.length <= 20);
	}

}
