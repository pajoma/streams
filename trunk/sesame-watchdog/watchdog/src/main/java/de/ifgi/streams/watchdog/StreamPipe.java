package de.ifgi.streams.watchdog;

import java.util.Observable;
import java.util.Observer;

public class StreamPipe extends Observable implements Runnable  {

	public void run() {
		while(true) {
			// just keep alive
		}
		
	}

	public void write(byte[] bytes) {
		this.setChanged(); 
		this.notifyObservers(bytes); 
		
	}

	

}
