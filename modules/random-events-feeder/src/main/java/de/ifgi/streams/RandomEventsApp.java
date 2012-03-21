package de.ifgi.streams;

import java.io.IOException;

public class RandomEventsApp {

	
	public static void main(String[] args) throws InterruptedException, IOException {
		RandomPusher pusher = new RandomPusher(); 
		pusher.start();
		
		RandomPoller poller = new RandomPoller(); 
		poller.setPusher(pusher); 
		poller.start();
		
		while(true) {
		
			Thread.sleep(5000); 
	
		
		}
		
		
	
	}
}
