package de.ifgi.streams.watchdog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Observable;
import java.util.Observer;

import org.restlet.data.MediaType;
import org.restlet.representation.WritableRepresentation;

public class ExtendedWritableRepresentation extends WritableRepresentation implements Observer {



	private WritableByteChannel writableChannel;

	public ExtendedWritableRepresentation(MediaType mediaType, Observable pipe) {
		super(mediaType);
		pipe.addObserver(this); 

	}
	


	@Override
	public void write(WritableByteChannel writableChannel) throws IOException {
		this.writableChannel = writableChannel;
		
		while( writableChannel.isOpen() ) {
			// we do nothing, we write through the update method
//			this.writableChannel.write(ByteBuffer.wrap( ".".getBytes())); 
		}
		writableChannel.close(); 

	}

	public void update(Observable o, Object arg) {
		try {
			if(this.writableChannel == null) return; 
			
			if(this.writableChannel.isOpen()) {
				this.writableChannel.write(ByteBuffer.wrap( (byte[]) arg)); 
			
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}

}
