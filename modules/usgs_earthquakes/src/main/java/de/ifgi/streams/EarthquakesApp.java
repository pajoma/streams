package de.ifgi.streams;

public class EarthquakesApp {

	
	public static void main(String[] args) throws InterruptedException {
		EarthQuakePusher pusher = new EarthQuakePusher(); 
		pusher.start();
		
		EarthQuakePoller poller = new EarthQuakePoller(); 
		poller.setPusher(pusher); 
		poller.start();
		
		while(true) {
		
			Thread.sleep(5000); 
	
		
		}
		
		
	
	}
}
