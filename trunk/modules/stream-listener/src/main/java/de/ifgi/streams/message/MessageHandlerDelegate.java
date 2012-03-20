package de.ifgi.streams.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Retrieves messages and distributes it to the appropriate handler
 * 
 * 
 * @author pajoma
 *
 */
public class MessageHandlerDelegate<T> extends DefaultConsumer {

	private MessageContainer<T> container; 
	
	Logger log = Logger.getLogger(this.getClass().getName()); 
	
	public MessageHandlerDelegate(Channel channel, MessageContainer<T>  container) {
		super(channel);
		this.container = container;
	}

	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
		// TODO: get syntax from message properties
		
		
		try {
			this.container.pushMessage(this.parseMessage(body, null));
		} catch (InterruptedException e) {
			log.info("Shutting down Message Listener"); 
		}  
		
	}
	
	
	
	public T parse(byte[] message, Map<String, Object> params) {
		for(MessageHandler<T> ms : listRegisteredMessageHandlers()) {
			if(ms.isResponsibleForMessage(message, params)) {
				return ms.handleMessage(message, params); 
			}
		}
		
		// do nothing, 
		return null; 
	}
	
	@SuppressWarnings("unchecked")
	public T parseMessage(byte[] message, Map<String, Object> params) {
		for(MessageHandler<?> ms : listRegisteredMessageHandlers()) {
			if(ms.isResponsibleForMessage(message, params)) {
				return (T) ms.handleMessage(message, params); 
			}
		}
		
		// do nothing, 
		return null; 
	
	}

	



	private  List<MessageHandler<T>> registeredMessageHandlers; 
	private  List<MessageHandler<T>> listRegisteredMessageHandlers() {
		if (registeredMessageHandlers == null) {
			registeredMessageHandlers = new ArrayList<MessageHandler<T>>(); 
			
		}

		return registeredMessageHandlers;
	}

	
	public void registerMessageHandler(MessageHandler<T> mh) {
		listRegisteredMessageHandlers().add(mh); 
	}
	
}
	