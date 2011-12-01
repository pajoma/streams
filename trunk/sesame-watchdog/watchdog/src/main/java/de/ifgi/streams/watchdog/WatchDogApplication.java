package de.ifgi.streams.watchdog;

import java.io.ByteArrayOutputStream;
import java.nio.channels.WritableByteChannel;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.routing.Router;

public class WatchDogApplication extends Application {
	
	private WritableByteChannel writableChannel;
	private ByteArrayOutputStream baos;
	private final StreamPipe pipe;
	
	public WatchDogApplication(StreamPipe pipe) {
		this.pipe = pipe;
		
	}
	
    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());


        
        baos = new ByteArrayOutputStream(); 
        
        Restlet r = new Restlet() {
        	
        	
        	@Override
        	public void handle(Request request, Response response) {
        		 
        		
        		response.setEntity(new ExtendedWritableRepresentation(MediaType.TEXT_PLAIN, pipe)); 
        	}
		};
		
        // Defines only one route
        router.attach("/listen/{repository}", r); 

        return router;
    }

	public ByteArrayOutputStream getStream() {
		return baos; 
	}
}
