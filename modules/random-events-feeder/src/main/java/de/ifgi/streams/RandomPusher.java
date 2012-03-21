package de.ifgi.streams;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RandomPusher extends Thread {
	Logger log = Logger.getLogger("EarthQuakes Pusher"); 
	public static String USER = "usgs"; 
	public static String PASS = "KjldyOVsOoOD3YxHBm37"; 
	

	public static String SERVER = "dunedin.uni-muenster.de";
	public static String QUEUE = "events";
	private Channel channel; 


	@Override
	public void run() {
		boolean run = true; 
		try {
			channel = this.initConnection();
		} catch (IOException e1) {
			throw new RuntimeException(e1); 
		} 
		
		while(run) {
			// we just keep our selves running
			
			try {

				Thread.sleep(2000); 
			
			} catch (InterruptedException e) {
				log.log(Level.SEVERE, "Thread", e); 
				run = false; 
				
			} 
		}
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

	private Channel initConnection() throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(SERVER);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE, false, false, false, null);

		return channel; 
	   
		
	}




	public  void push(String message) throws IOException {

		channel.basicPublish("", QUEUE, null, message.getBytes());
		System.out.println(" [x] Sent Event");
//			
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

}
