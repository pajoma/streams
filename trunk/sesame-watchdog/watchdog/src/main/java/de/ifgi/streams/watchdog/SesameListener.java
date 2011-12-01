package de.ifgi.streams.watchdog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channel;

public class SesameListener implements Runnable {

	private final StreamPipe pipe;
	
	public SesameListener(StreamPipe pipe) {
		this.pipe = pipe;
	}
	
	public void run() {
	
		int c = 0; 
		while(true) {
			
			try {
				
				
				String line = "line "+ c++ +'\n'; 

				this.pipe.write(line.getBytes());
				
				Thread.sleep(500); 
			}  catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
	}
	

}
	