package de.ifgi.streams.ampq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private final static String QUEUE_NAME = "events";

	public static void main(String[] argv) throws java.io.IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("dunedin.uni-muenster.de");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    String message = "Hello World!";
	    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	    System.out.println(" [x] Sent '" + message + "'");
	    
	    channel.close();
	    connection.close();
	}
}