package de.ifgi.streams.listener;

import java.io.IOException;
import java.net.UnknownHostException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import de.ifgi.streams.message.MessageContainer;
import de.ifgi.streams.message.MessageHandlerDelegate;


public class StreamListener<T> implements Runnable  {
	
	private MessageContainer<T> container; 
	
	private Channel channel;
	
	private Connection connection;
	private final String queue;
	private final String host;

	
	public StreamListener(String queue, String host) throws IOException {
		this.queue = queue;
		this.host = host;
		this.init(); 
	}


	public void init() throws IOException {
		connection = null; 
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(queue, false, false, false, null);
			
			
			
			
		} catch(UnknownHostException e) {
			// operating in offline mode
		} catch (IOException e) {
			
			throw e;
		} 

	}
	public void run() {
		try {
			boolean autoAck = false; 
			channel.basicConsume(queue, autoAck, getMessageHandlerDelegate());
			
			while(true) {
				// do nothing
				Thread.sleep(10); 
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
	
	}


	public Channel getChannel() {
		return this.channel; 
	}


	MessageHandlerDelegate<T> messageDelegator = null; 
	
	public MessageHandlerDelegate<T> getMessageHandlerDelegate() {
		if (messageDelegator == null) {
			messageDelegator = new MessageHandlerDelegate<T>(channel, container); 
			
		}

		return messageDelegator;
		
	}

	
	

}
