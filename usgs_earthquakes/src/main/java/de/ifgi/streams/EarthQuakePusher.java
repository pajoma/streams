package de.ifgi.streams;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class EarthQuakePusher extends Thread {
	Logger log = Logger.getLogger("EarthQuakes Pusher"); 
	public static String USER = "usgs"; 
	public static String PASS = "KjldyOVsOoOD3YxHBm37"; 
	

	public static String SERVER = "dunedin.uni-muenster.de";
	public static String CONFERENCE = "earthquakes@conference.dunedin.uni-muenster.de"; 
	
	private MultiUserChat muc = null; 

	private XMPPConnection connection;


	@Override
	public void run() {
	
		
		while(true) {
			// we just keep our selves running
			
			try {
				
			
				Thread.sleep(300);
			
			} catch (InterruptedException e) {
				log.log(Level.SEVERE, "Thread", e); 
				
			} 
		}
	}
	
	
	private XMPPConnection getConnection() throws XMPPException {
		if ((connection == null)||(!connection.isConnected())) {
					
			ConnectionConfiguration config = new ConnectionConfiguration(SERVER, 5222);
			config.setCompressionEnabled(true);
			config.setSASLAuthenticationEnabled(true);  
//			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			
			connection = new XMPPConnection(config);
			// Connect to the server
			connection.connect();
			connection.login(USER, PASS); 
			log.info(USER +" is successfully connected"); 
		}

		return connection;
	
	}
	
//	private XMPPConnection getConnection() throws XMPPException {
//		if (connection == null) {
//			
//			ConnectionConfiguration config = new ConnectionConfiguration(SERVER, 5222);
//			config.setCompressionEnabled(true);
//			config.setSASLAuthenticationEnabled(true);  
////			 SASLAuthentication.supportSASLMechanism("PLAIN", 0);
//			
//			connection = new XMPPConnection(config);
//			
//		}
//		
//		if(! connection.isConnected()) {
//			
//			// Connect to the server
//			connection.connect();
//		
//		}
//		
//		if(! connection.isAuthenticated()) {
//			connection.login(USER, PASS); 
//			
//		    
//		
//		}
//		log.info(USER +" is successfully connected"); 
//		return connection;
//	
//	}

	public  void push(String message) throws IOException {
		try {
			
			
			getConference().sendMessage(message);
			
		} catch (XMPPException e) {
			throw new IOException(e); 
		} 
	}

//	private  MultiUserChat getConference() throws XMPPException {
//		if (muc == null) {
//			muc = new MultiUserChat(getConnection(), CONFERENCE);
//			
//		}
//		if (! muc.isJoined()) {
//			muc.join(USER); 
//			log.info(USER+" joined conference"); 
//		} 
//
//		return muc;
//
//		
//	}


	private  MultiUserChat getConference() throws XMPPException {
		if (muc == null) {
			muc = new MultiUserChat(getConnection(), CONFERENCE);
			
		}
		if (! muc.isJoined()) {
			muc.join(USER); 
			log.info(USER+" joined conference"); 
		} 
	
//		else {
//			muc = null; 
//			return getConference(); 
//		}

		return muc;

		
	}
}
