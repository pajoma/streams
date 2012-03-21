package de.ifgi.streams.restlet;

import java.io.IOException;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.routing.Router;

import de.ifgi.streams.RandomPoller;
import de.ifgi.streams.RandomPusher;


public class RestletApplication extends Application {

	public RestletApplication() throws IOException {
		// start threads
		RandomPusher pusher = new RandomPusher(); 
		pusher.start();
		
		RandomPoller poller = new RandomPoller(); 
		poller.setPusher(pusher); 
		poller.start();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		Restlet r = new Restlet() {

			@Override
			public void handle(Request request, Response response) {

				response.setEntity("Hello there", MediaType.TEXT_PLAIN);
			}
		};

		// Defines only one route
		router.attach("/", r);

		return router;
	}

}
