package de.ifgi.streams.pusher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;

import org.junit.Test;

import de.ifgi.streams.listener.StreamListener;
import de.ifgi.streams.message.MessageContainer;
import de.ifgi.streams.message.MessageHandler;

public class TestMessagePushing {
	
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private String result = "";
	
	@Test
	public void testPush() {
		StreamListener<String> listener;
		try {
			listener = new StreamListener<String>(MessagePusher.defaultQUEUE, MessagePusher.defaultSERVER, new MessageContainer<String>());
			MessageHandler<String> mhd1 = new TestMessageHandler();
			listener.getMessageHandlerDelegate().registerMessageHandler(mhd1); 
			
			Thread t = new Thread(listener);
			t.start();
			
			TestObserver testObs = new TestObserver();
			testObs.setEventListener(listener);
			testObs.start();
			
			MessagePusher pusher = new MessagePusher();
			String message = "TESTMESSAGE from "+now();
			pusher.push(message);
			Thread.sleep(2000);
			
			Assert.assertEquals(message, result);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Assert.fail();
		} 
	}
	
	public static String now() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());

	  }
	
	public class TestMessageHandler implements MessageHandler<String>{

		@Override
		public String handleMessage(byte[] message, Map<String, Object> params) {
			return new String(message);
		}

		@Override
		public boolean isResponsibleForMessage(byte[] message, Map<String, Object> params) {
			String msg = new String(message); 
			return msg.contains("TESTMESSAGE");
		}
		
	}
	
	public class TestObserver extends Thread implements Observer {
		
		public void setEventListener(StreamListener<String> listener) {
			listener.getMessageContainer().addObserver(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			System.out.println("update");
			if (arg instanceof String) {
				System.out.println(arg);
				result = (String) arg;
			}
		}

		@Override
		public void run() {
			while(true){
				
			}
		}
		
	}

}


