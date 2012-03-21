package de.ifgi.streams;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jdom.Element;
import org.omg.CORBA._PolicyStub;
import org.openrdf.model.BNode;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.RDFWriterRegistry;
import org.openrdf.rio.n3.N3Writer;
import org.openrdf.sail.memory.MemoryStore;

import com.sun.syndication.feed.synd.SyndEntry;

public class MessageBuilder {
	
	public static String NS = "http://purl.org/ifgi/event-observation#";
	public static String NS_DO = "http://purl.org/ifgi/earthquakes#"; 
	public static String NS_DC = "http://purl.org/dc/elements/1.1/"; 
	public static String NS_SSN = "http://purl.org/ifgi/ssn#"; 
	public static String NS_DUL = "http://purl.org/ifgi/dul#";
	public static String NS_GEOSPARQL = "http://purl.org/ifgi/geosparql#"; 
	public static String NS_TIME = "http://www.w3.org/2006/time#"; 
	
	

	private Random random;
	private Repository myRepository;
	private ValueFactory vf;
	

	public MessageBuilder() throws RepositoryException {
		myRepository = new SailRepository(new MemoryStore());
		myRepository.initialize();
		
		vf = myRepository.getValueFactory();
		
		random = new Random(); 
		
		
	}
	
	List<Statement> stack = new ArrayList<Statement>(); 
	private void stackStatement(Resource subject, URI predicate, Value object ) {
		stack.add(vf.createStatement(subject, predicate, object));  
	}
	
	public void constructMessage(StringWriter sw, SyndEntry entry) throws RepositoryException, RDFHandlerException {
		RepositoryConnection con = myRepository.getConnection(); 
		try {
			this.createStatements(entry); 
			con.add(stack); 
			
			N3Writer n3Writer = new N3Writer(sw); 
			con.export(n3Writer); 	
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			con.close(); 
		}
		
		
		
	
	
		
		
		
		// parse message
		
		
		// build O&M documents
	}

	private void createStatements(SyndEntry entry) {
		String[] split = entry.getTitle().split(",");

		String location, magnitude, time, depth; 
		
		magnitude = split[0]; 
		time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(entry.getUpdatedDate()); 
		Object[] fm  = ((ArrayList<Object>) entry.getForeignMarkup()).toArray(); 
		location = "POINT("+((Element) fm[0]).getValue()+")";
		depth = ((Element) fm[1]).getValue(); 
		
		
		/** CLASSES **/
		BNode _eventObservation = vf.createBNode(); 
		stackStatement(_eventObservation, RDF.TYPE, vf.createURI(NS, "EventObservation")); 
		
		BNode _eventDescription = vf.createBNode(); 
		stackStatement(_eventDescription, RDF.TYPE, vf.createURI(NS, "EventObservationDescription")); 
		 
		BNode _observedProperty = vf.createBNode(); 
		stackStatement(_observedProperty, RDF.TYPE, vf.createURI(NS_DO, "GroundMotion"));
		
		BNode _observedResult = vf.createBNode(); 
		stackStatement(_observedResult, RDF.TYPE, vf.createURI(NS_DO, "Magnitude"));
		
		
		BNode _stimulus = vf.createBNode(); 
		stackStatement(_stimulus, RDF.TYPE, vf.createURI(NS_SSN, "Stimulus"));
		
		BNode _observer = vf.createBNode(); 
		stackStatement(_observer, RDF.TYPE, vf.createURI(NS_DO, "Seismograph"));
		
		BNode _eventDetectionProcedure = vf.createBNode(); 
		stackStatement(_eventDetectionProcedure, RDF.TYPE, vf.createURI(NS, "EventDetectionProcedure"));
		
		BNode _eventObservationType = vf.createBNode(); 
		stackStatement(_eventObservationType, RDF.TYPE, vf.createURI(NS_DO, "EarthQuake"));
		
		BNode _observationValue = vf.createBNode(); 
		stackStatement(_observationValue, RDF.TYPE, vf.createURI(NS, "EventObservationValue"));
		
		BNode _sensorOutput = vf.createBNode(); 
		stackStatement(_sensorOutput, RDF.TYPE, vf.createURI(NS_SSN, "SensorOutput"));
//		
		BNode _spaceRegionDescription = vf.createBNode(); 
		stackStatement(_spaceRegionDescription, RDF.TYPE, vf.createURI(NS_DUL, "Description"));
		stackStatement(_spaceRegionDescription, RDF.TYPE, vf.createURI(NS_GEOSPARQL, "Geometry"));
//		
		BNode _timeIntervalDescription = vf.createBNode(); 
		stackStatement(_timeIntervalDescription, RDF.TYPE, vf.createURI(NS_TIME, "TemporalEntity"));
		stackStatement(_timeIntervalDescription, RDF.TYPE, vf.createURI(NS_DUL, "Description"));
//		
//		BNode _spatioTemporalLocation = vf.createBNode(); 
//		stackStatement(_spatioTemporalLocation, RDF.TYPE, vf.createURI(NS_DUL, "SpatioTemporalRegion"));
		
		
		/** RELATIONS **/
		stackStatement(_eventDescription, vf.createURI(NS_DC, "title"), vf.createLiteral("Earthquake Event Description"));  
		stackStatement(_eventDescription, vf.createURI(NS, "observation"), _eventObservation);
		stackStatement(_eventDescription, vf.createURI(NS, "eventTime"), _timeIntervalDescription);
		stackStatement(_eventDescription, vf.createURI(NS, "eventLocation"), _spaceRegionDescription);
		stackStatement(_eventDescription, vf.createURI(NS_DUL, "describes"), _eventObservationType);
		
		stackStatement(_spaceRegionDescription, vf.createURI(NS_GEOSPARQL, "asWKT"), vf.createLiteral(location));
		
		stackStatement(_spaceRegionDescription, vf.createURI(NS_TIME, "inXSDDateTime"), vf.createLiteral(time));
		

		stackStatement(_eventObservationType, vf.createURI(NS_SSN, "observedProperty"), _observedProperty);
		stackStatement(_eventObservationType, vf.createURI(NS_SSN, "observationResult"), _sensorOutput);
		stackStatement(_sensorOutput, vf.createURI(NS_SSN, "hasValue"), vf.createLiteral(magnitude));
	
		
		
	}


}
