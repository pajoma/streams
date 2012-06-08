package de.ifgi.streams.message;

import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.Observable;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Provides access to all messages which are coming from AMPQ. 
 * The MessageContainer is managed by Spring, and is the only class
 * shared between the ampg classes and the front end controllers
 * 
 * @author pajoma
 *
 */

public class MessageContainer<T> extends Observable {

	private BlockingDeque<Message<T>> list = null;
	
	public MessageContainer() {
		list = new LinkedBlockingDeque<MessageContainer<T>.Message<T>>(100); 
	}
	
	
	public synchronized Deque<Message<T>> listMessages() {
		return list; 
	}
	
	/**
	 * Retrieves (but not removes) next message
	 * @return
	 */
	public synchronized T peek() {
		return list.peek().message; 
	}
	
	/**
	 * Retrieves (and removes) next message
	 * @return
	 * @throws InterruptedException 
	 */
	public synchronized T poll() throws InterruptedException {
		return list.takeLast().message; 
	}
	
	public void pushMessage(T message) throws InterruptedException {
		setChanged();
		Message<T> m = new Message<T>(); 
		m.message = message; 
		m.stored = Calendar.getInstance().getTime(); 
		
		if(!listMessages().offer(m)) {
			poll();
			clearChanged();
			pushMessage(message);  
		}else{
			notifyObservers(m.message);
			clearChanged();
		}
		
	}
	
	
	class Message<T> {
		T message = null; 
		Date stored = null; 
	}


	public boolean hasNext() {
		return ! listMessages().isEmpty(); 
	}
}
