package de.ifgi.streams.watchdog;

import java.io.ByteArrayOutputStream;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;

public class WatchDogMain extends ServerResource {
	 
	private ByteArrayOutputStream stream;
	private StreamPipe pipe;


	public void init() throws Exception {
		this.initPipe(); 
		this.initStreamingService();
		this.initSesameListener(); 
	}
	
	
	private void initPipe() {
		this.pipe = new StreamPipe();
		Thread t = new Thread(pipe, "Stream Pipe"); 
		t.start(); 
	}


	private void initSesameListener() {
		Thread t = new Thread(new SesameListener(pipe), "Sesame Listener"); 
		t.start(); 
	      
	     
		
	}


	private void initStreamingService() throws Exception {
		 Component component = new Component();

	        // Add a new HTTP server listening on port 8111.
	        component.getServers().add(Protocol.HTTP, 8182);

	        // Attach the sample application.
	        WatchDogApplication wda = new WatchDogApplication(this.pipe); 
	        component.getDefaultHost().attach("/api", wda);

	        // Start the component.
	        component.start();
	        
	        this.stream = wda.getStream(); 
		
	}


	public static void main(String[] args) throws Exception {  
		  new WatchDogMain().init(); 

	   }
	  

}
