package de.ifgi.streams;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class EarthQuakePoller extends Thread{
	
	public static String FEEDURL = "http://earthquake.usgs.gov/earthquakes/catalogs/1hour-M0.xml";
	private EarthQuakePusher pusher; 
	
	@Override
	public void run() {
		while(true) {
			// we check every 60 minutes
			try {
				
				this.poll(); 
				
				Thread.sleep(6000000);
				
				
			} catch (InterruptedException e) {
				break; 
			} catch (IllegalArgumentException e) {
				break; 
			} catch (MalformedURLException e) {
				break; 
			} catch (FeedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					break; 
				}
			} 
		}
	}

	private void poll() throws IllegalArgumentException, MalformedURLException, FeedException, IOException {
		 SyndFeedInput input = new SyndFeedInput();
		 SyndFeed feed = input.build(new XmlReader(new URL(FEEDURL))); 
		List<SyndEntry> entries = (List<SyndEntry>) feed.getEntries(); 
			for (SyndEntry entry : entries) {
				// build message
				StringBuilder sb = new StringBuilder(); 
				new MessageBuilder().constructMessage(sb, entry); 
				this.pusher.push(sb.toString()); 
				
			}
	}


	public void setPusher(EarthQuakePusher pusher) {
		this.pusher = pusher;

		
	}


	
	
	
}
