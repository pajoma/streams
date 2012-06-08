package de.ifgi.streams;

import static org.junit.Assert.*;

import java.util.Collection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Body;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.junit.Test;

public class TestListening {

	@Test
	public void test() {
		ConnectionConfiguration config = new ConnectionConfiguration("giv-wfs.uni-muenster.de", 5222);
		config.setCompressionEnabled(true);
		config.setSASLAuthenticationEnabled(true);  
		SASLAuthentication.supportSASLMechanism("PLAIN", 0);

		XMPPConnection connection = new XMPPConnection(config);
		// Connect to the server
		try {
			connection.connect();
		
			connection.login("earthquakes_usgs", "msdwqdswr"); 
			
			MultiUserChat muc = new MultiUserChat(connection, "earthquakes@conference.giv-wfs.uni-muenster.de"); 
			muc.join("earthquakes"); 
	
		    // Accept only messages from HQ
//		    PacketFilter filter 
//		        = new AndFilter(new PacketTypeFilter(Message.class), 
//		                        new FromContainsFilter("headquaters@mycompany.com"));
	
		    PacketListener myListener = new PacketListener() {
		        public void processPacket(Packet packet) {
		            if (packet instanceof Message) {
		                Message msg = (Message) packet;
		                // Process message
		                Collection<Body> bodies = msg.getBodies();
		                Collection<String> propertyNames = msg.getPropertyNames();
//		                System.out.println(bodies.size());
		                
		                for (String string : propertyNames) {
							System.out.println(string);
						}
		                
//		                int counter = 0;
		                for (Body body : bodies) {
							System.out.println(body.getMessage());
//							System.out.println(counter);
//							counter++;
						}
		                
		            }
		        }
		    };
		    // Register the listener.
		    muc.addMessageListener(myListener);
			
			PacketCollector collector = connection.createPacketCollector(new PacketTypeFilter(Message.class));
			
		    
		    while(true){
//		    	System.out.println(muc.nextMessage());
		    	
//		    	Packet packet = collector.nextResult();
//		        if (packet instanceof Message) {
//		            Message msg = (Message) packet;
//		            System.out.println(msg.getType());
//		        }
		    }
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
