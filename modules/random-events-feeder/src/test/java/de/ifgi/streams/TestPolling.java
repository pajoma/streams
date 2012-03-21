package de.ifgi.streams;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.junit.Test;

import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class TestPolling {

	
	@Test
	public void testParseFeed() {
		 SyndFeedInput input = new SyndFeedInput();
		 SyndFeed feed;
		try {
			feed = input.build(new XmlReader(new URL("http://earthquake.usgs.gov/earthquakes/catalogs/1hour-M0.xml")));
			List<SyndEntry> entries = feed.getEntries(); 
			for (SyndEntry entry : entries) {
				/*
				 <entry>
				 <id>urn:earthquake-usgs-gov:ci:10921933</id>
				 <title>M 1.5, Southern California</title>
				 <updated>2011-04-08T15:17:36Z</updated>
				 <link rel="alternate" type="text/html" href="http://earthquake.usgs.gov/earthquakes/recenteqsus/Quakes/ci10921933.php"/>
				 <summary type="html"><![CDATA[<img src="http://earthquake.usgs.gov/images/globes/35_-115.jpg" alt="32.994&#176;N 116.339&#176;W" align="left" hspace="20" /><p>Friday, April  8, 2011 15:17:36 UTC<br>Friday, April  8, 2011 08:17:36 AM at epicenter</p><p><strong>Depth</strong>: 12.00 km (7.46 mi)</p>]]></summary>
				 <georss:point>32.9940 -116.3390</georss:point>
				 <georss:elev>-12000</georss:elev>
				 <category label="Age" term="Past hour"/>
				 </entry> 
				 * 
				 */
				
				System.out.println(entry.getTitle());
				String[] split = entry.getTitle().split(",");
				String location, magnitude, time, depth; 
				
				magnitude = split[0]; 
				time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(entry.getUpdatedDate()); 
				Object[] fm  = ((ArrayList<Object>) entry.getForeignMarkup()).toArray(); 
				location = "POINT("+((Element) fm[0]).getValue()+")";
				depth = ((Element) fm[1]).getValue(); 
				
				System.out.println(magnitude);
				System.out.println(time);
				System.out.println(location);
				System.out.println(depth);
//				entry.get
			}
			
			 
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
	}
}
