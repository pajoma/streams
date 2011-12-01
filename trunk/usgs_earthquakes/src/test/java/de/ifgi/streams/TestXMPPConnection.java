package de.ifgi.streams;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.junit.Test;

public class TestXMPPConnection {

	
	@Test
	public void testConnect() throws XMPPException {
		ConnectionConfiguration config = new ConnectionConfiguration("giv-wfs.uni-muenster.de", 5222);
		config.setCompressionEnabled(true);
		config.setSASLAuthenticationEnabled(true);  
		SASLAuthentication.supportSASLMechanism("PLAIN", 0);

		XMPPConnection connection = new XMPPConnection(config);
		// Connect to the server
		connection.connect();
		connection.login("earthquakes_usgs", "msdwqdswr"); 
		

		ChatManager chatmanager = connection.getChatManager();
		
		Chat newChat = chatmanager.createChat("admin@giv-wfs.uni-muenster.de", new MessageListener() {

			public void processMessage(Chat chat, Message message) {
				System.out.println("Received message: " + message);
			}
			
		}); 
		
		try {
		    newChat.sendMessage("Howdy!");
		}
		catch (XMPPException e) {
		    System.out.println("Error Delivering block");
		}
		
		while(true) {
			
		}
	}
	
	@Test
	public void testConference() throws XMPPException {
		ConnectionConfiguration config = new ConnectionConfiguration("giv-wfs.uni-muenster.de", 5222);
		config.setCompressionEnabled(true);
		config.setSASLAuthenticationEnabled(true);  
		SASLAuthentication.supportSASLMechanism("PLAIN", 0);

		XMPPConnection connection = new XMPPConnection(config);
		// Connect to the server
		connection.connect();
		connection.login("earthquakes_usgs", "msdwqdswr"); 
		
		MultiUserChat muc = new MultiUserChat(connection, "earthquakes@conference.giv-wfs.uni-muenster.de"); 
		muc.join("earthquakes"); 
		
		muc.sendMessage("was geht ab");
		
		
		while(true) {
			
		}
		 
	}
}
