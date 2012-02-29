package de.ifgi.streams.restlet;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.routing.Router;

import de.ifgi.streams.EarthQuakePoller;
import de.ifgi.streams.EarthQuakePusher;


public class RestletApplication extends Application {

	public RestletApplication() {
		// start threads
		EarthQuakePusher pusher = new EarthQuakePusher(); 
		pusher.start();
		
		EarthQuakePoller poller = new EarthQuakePoller(); 
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
