package de.ifgi.streams.watchdog;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.restlet.data.MediaType;
import org.restlet.representation.ChannelRepresentation;
import org.restlet.representation.WritableRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

@Deprecated
public class WatchDogRestlet extends ServerResource {

	  @Get  
	   public ChannelRepresentation getStream() {  
	      return new WritableRepresentation(MediaType.TEXT_PLAIN) {
			
			@Override
			public void write(WritableByteChannel writableChannel) throws IOException {
				int c = 0; 
				while(true) {
					
				}
				
			}
		};
	   }
	  
	   
	   public String toString() {  
		  return "Hello there"; 
	   }
}
