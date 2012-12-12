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
	private String result1 = "";
	private String result2 = "";
	
	@Test
	public void testPush() {
		StreamListener<String> listener;
		StreamListener<String> listener2;
		try {
			listener = new StreamListener<String>(MessagePusher.defaultEXCHANGE, MessagePusher.defaultSERVER, new MessageContainer<String>());
			MessageHandler<String> mhd1 = new TestMessageHandler();
			listener.getMessageHandlerDelegate().registerMessageHandler(mhd1);
			
			Thread t = new Thread(listener);
			t.start();
			
			TestObserver testObs = new TestObserver();
			testObs.setEventListener(listener);
			testObs.start();
			
			listener2 = new StreamListener<String>(MessagePusher.defaultEXCHANGE, MessagePusher.defaultSERVER, new MessageContainer<String>());
			MessageHandler<String> mhd2 = new TestMessageHandler2();
			listener2.getMessageHandlerDelegate().registerMessageHandler(mhd2);
			
			Thread t2 = new Thread(listener2);
			t2.start();
			
			TestObserver2 testObs2 = new TestObserver2();
			testObs2.setEventListener(listener2);
			testObs2.start();
			
			MessagePusher pusher = new MessagePusher();
			String message = "TESTMESSAGE from "+now();
			pusher.push(message);
			String message2 = "HALLO from "+now();
			pusher.push(message2);
			Thread.sleep(2000);
			
			Assert.assertEquals(message, result1);
			Assert.assertEquals(message2, result2);
			pusher.shutdown();
			listener.shutdown();
			listener2.shutdown();
			testObs = null;
			t2 = null;
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
	
	public class TestMessageHandler2 implements MessageHandler<String>{

		@Override
		public String handleMessage(byte[] message, Map<String, Object> params) {
			return new String(message);
		}

		@Override
		public boolean isResponsibleForMessage(byte[] message, Map<String, Object> params) {
			String msg = new String(message); 
			return msg.contains("HALLO");
		}
		
	}
	
	public class TestObserver extends Thread implements Observer {
		
		public void setEventListener(StreamListener<String> listener) {
			listener.getMessageContainer().addObserver(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			if (arg instanceof String) {
				String retrieval = (String) arg;
				if (retrieval.contains("MESSAGE")) {
					System.out.println(retrieval);
					result1 = retrieval;
				}
			}
		}

		@Override
		public void run() {
			while(true){
				
			}
		}
		
	}
	
public class TestObserver2 extends Thread implements Observer {
		
		public void setEventListener(StreamListener<String> listener) {
			listener.getMessageContainer().addObserver(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			if (arg instanceof String) {
				String retrieval = (String) arg;
				if (retrieval.contains("HALLO")) {
					System.out.println(retrieval);
					result2 = retrieval;
				}
			}
		}

		@Override
		public void run() {
			while(true){
				
			}
		}
		
	}

}


