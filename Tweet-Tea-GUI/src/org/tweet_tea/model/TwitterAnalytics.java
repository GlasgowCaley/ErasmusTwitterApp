package org.tweet_tea.model;

import java.util.ArrayList;
import java.util.List;

public final class TwitterAnalytics {

public static List<TopHashtags> getTopHashtags(Tweet[] Total) throws Exception{
		
		boolean found;
		List<TopHashtags> list = new ArrayList<TopHashtags>();
		
		for(int i=0;i<Total.length;i++)
		{
			if(Total[i].getEntities().getHashtags()!=null){
				for(int j=0;j<Total[i].getEntities().getHashtags().length;j++){
					found = false;
					for(TopHashtags hashtag : list)
					if(hashtag.Hashtag==Total[i].getEntities().getHashtags()[j].getText()){
						found=true;
						hashtag.count++;
					}
					if(!found)
						list.add(new TopHashtags(Total[i].getEntities().getHashtags()[j].getText()));
				}		
			}
		}
		
		return list;
	}	
}
