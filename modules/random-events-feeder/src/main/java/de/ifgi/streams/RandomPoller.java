package de.ifgi.streams;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandlerException;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RandomPoller extends Thread{
	
	public static String FEEDURL = "http://earthquake.usgs.gov/earthquakes/catalogs/1hour-M0.xml";
	private RandomPusher pusher;
	private String template; 
	
	public RandomPoller() throws IOException {
		InputStream is = new BufferedInputStream(this.getClass().getResourceAsStream("template.n3")); 
		StringWriter sw = new StringWriter(); 
		IOUtils.copy(is, sw); 
		template = sw.toString(); 
		
	}
	
	@Override
	public void run() {
		Random r = new Random(); 
		 
		
		while(true) {
			// we check every 60 minutes
			try {
				String copy = new String(template); 
				
				float latitude =   (((float) r.nextInt(1800)) -900f)/10f; //latitude
				float longitude =   (((float) r.nextInt(3600)) -1800f)/10f; //longitude
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
				sdf.format(Calendar.getInstance().getTime()); 
				
				copy = copy.replace("{X-COORD}", ""+longitude); 
				copy = copy.replace("{Y-COORD}", ""+latitude); 
				copy = copy.replace("{DATE}", ""+sdf.format(Calendar.getInstance().getTime())); 
				
				Thread.sleep(10000);
				this.pusher.push(copy); 
				
				
			} catch (InterruptedException e) {
				e.printStackTrace(); 
				break; 
			} catch (IllegalArgumentException e) {
				e.printStackTrace(); 
				break; 
			} catch (IOException e) {
				e.printStackTrace(); 
			} 
		}
	}

	private void poll(MessageBuilder mb) throws IllegalArgumentException, MalformedURLException, FeedException, IOException, RepositoryException, RDFHandlerException {
		 SyndFeedInput input = new SyndFeedInput();
		 SyndFeed feed = input.build(new XmlReader(new URL(FEEDURL))); 
		List<SyndEntry> entries = (List<SyndEntry>) feed.getEntries(); 
			for (SyndEntry entry : entries) {
				// build message
				StringWriter sw = new StringWriter(); 
				mb.constructMessage(sw, entry); 
				
				
			}
	}


	public void setPusher(RandomPusher pusher) {
		this.pusher = pusher;

		
	}


	
	
	
}
