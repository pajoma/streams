package de.ifgi.streams.pusher;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessagePusher{
	
//	private static EventPusher instance;
	
	Logger log = Logger.getLogger(MessagePusher.class.getName()); 
//	public static String USER = "usgs"; 
//	public static String PASS = "KjldyOVsOoOD3YxHBm37"; 
	
	//public static String CONFERENCE = "earthquakes@conference.dunedin.uni-muenster.de"; 
//	public static String USER = "flood_alerts"; 
//	public static String PASS = "flood"; 
//	public static String SERVER = "giv-wfs.uni-muenster.de";
//	public static String CONFERENCE = "earthquakes@conference.giv-wfs.uni-muenster.de";
	
	public static final String defaultSERVER = "dunedin.uni-muenster.de";
//	public static final String defaultQUEUE = "events";
	public static final String defaultEXCHANGE = "events";
	private String server;
//	private String queue;
	private String exchange;
	private Channel channel;
	private Connection connection;
	
//	private MultiUserChat muc; 
//	private XMPPConnection connection;	

	//private EventPusher() {}
	
	public MessagePusher() throws IOException{
		server = defaultSERVER;
//		queue = defaultQUEUE;
		exchange = defaultEXCHANGE;
		initConnection();
	}
	
	public MessagePusher(String server, String exchange) throws IOException{
		this.server = server;
//		this.queue = queue;
		this.exchange = exchange;
		initConnection();
	}
	
	
//	public static EventPusher getEventPusher() {
//		if (instance == null) {
//			// get current context classloader                                                                                                                                  
//			ClassLoader contextClassloader = Thread.currentThread().getContextClassLoader();
//			// then alter the class-loader (but which one ? the one used to load this class itself) with:
//			Thread.currentThread().setContextClassLoader(EventProcessingServiceProvider.class.getClassLoader());
//			
//			instance = new EventPusher();
//			
//			// restore the class loader to its original value:
//			Thread.currentThread().setContextClassLoader(contextClassloader);
//		}
//		return instance;
//	}
	
	
//	public Object clone() throws CloneNotSupportedException {
//		throw new CloneNotSupportedException();
//	}


//	public void run() {
//		boolean run = true; 
//		try {
//			channel = this.initConnection();
//		} catch (IOException e1) {
//			throw new RuntimeException(e1); 
//		} 
		
//		while(run) {
//			// we just keep our selves running
//			
//			try {
//
//				Thread.sleep(300);
//			
//			} catch (InterruptedException e) {
//				log.log(Level.SEVERE, "Thread", e); 
//				run = false; 
//				
//			} 
//		}
//	}
	
	private void initConnection() throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(server);
		connection = factory.newConnection();
		
		channel = connection.createChannel();
//		channel.exchangeDeclare(exchange, "fanout");
		channel.exchangeDeclarePassive(exchange);
	}
	
	
	public void push(String message) throws IOException{
		try {
//			channel = connection.createChannel();
//			channel.queueDeclarePassive(queue);
//			channel.queueDeclare(queue, false, false, false, null);
			if(channel.isOpen()) {
				channel.basicPublish(exchange, "", null, message.getBytes());
				log.log(Level.FINE, " [x] Sent '" + message + "'");
				
			}else{
				throw channel.getCloseReason();
			}
			
		} catch (IOException e) {
			log.log(Level.WARNING, " [x] NOT Sent '" + message + "'");
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public void shutdown() throws IOException{
		channel.close();
		connection.close();
	}
	
	
//	public void push(String message) throws IOException {
//		try {			
//			getConference().sendMessage(message);
//			Thread.sleep(500);
//		} catch (XMPPException e) {
//			throw new IOException(e); 
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//	}
//	
//	private  MultiUserChat getConference() throws XMPPException {
//		if (muc == null) {
//			muc = new MultiUserChat(getConnection(), CONFERENCE);
//		}
//		if (! muc.isJoined()) {
//			muc.join(USER); 
//			log.info(USER+" joined conference"); 
//		} 
////		else {
////			muc = null; 
////			return getConference(); 
////		}
//		return muc;
//	}
//	
//	private XMPPConnection getConnection() throws XMPPException {
//		if ((connection == null)||(!connection.isConnected())) {		
//			ConnectionConfiguration config = new ConnectionConfiguration(SERVER, 5222);
//			config.setCompressionEnabled(true);
//			config.setSASLAuthenticationEnabled(true);  
//			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
//			connection = new XMPPConnection(config);
//			// Connect to the server
//			connection.connect();
//			connection.login(USER, PASS); 
//			log.info(USER +" is successfully connected"); 
//		}
//		return connection;
//	
//	}
}
