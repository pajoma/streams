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
//	private final String queue;
	private final String host;
	private final String exchange;
	private String queueName;
	
	private boolean run = true;
	
	private MessageHandlerDelegate<T> messageDelegator = null;
	
	public StreamListener(String exchange, String host) throws IOException {
		this.exchange = exchange;
		this.host = host;
		this.init(); 
	}
	
	public StreamListener(String exchange, String host, MessageContainer<T> container) throws IOException {
		this.exchange = exchange;
		this.host = host;
		this.container = container;
		this.init(); 
	}


	public void init() throws IOException {
		connection = null; 
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			
			connection = factory.newConnection();
			channel = connection.createChannel();
//			channel.queueDeclare(queue, false, false, false, null);
//			channel.queueDeclarePassive(queue);
			
			queueName = channel.queueDeclare().getQueue();
			
			// all message posted to our exchange will be posted to our new queue
			channel.queueBind(queueName, exchange, "");
		} catch(UnknownHostException e) {
			// operating in offline mode
		} catch (IOException e) {
			
			throw e;
		} 

	}
	public void run() {
		try {
			boolean autoAck = true; 
			channel.basicConsume(queueName, autoAck, getMessageHandlerDelegate());
			
			while(run) {
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


	 
	
	public MessageHandlerDelegate<T> getMessageHandlerDelegate() {
		if (messageDelegator == null) {
			messageDelegator = new MessageHandlerDelegate<T>(channel, getMessageContainer()); 
			
		}

		return messageDelegator;
		
	}

	public MessageContainer<T> getMessageContainer() {
		if (container == null) {
			container = new MessageContainer<T>(); 
		}
		return container;
	}
	
	public void shutdown(){
		try {
			run = false;
			channel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
