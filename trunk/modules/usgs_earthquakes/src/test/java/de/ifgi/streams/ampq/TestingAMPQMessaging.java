package de.ifgi.streams.ampq;

import java.io.IOException;

import org.junit.Test;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestingAMPQMessaging {

	
	@Test
	public void testing() throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("dunedin.uni-muenster.de");
		factory.setPort(5672);
		Connection conn = factory.newConnection();
		
		
	}
}
