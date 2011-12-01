package de.ifgi.streams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import org.jdom.Element;

import com.sun.syndication.feed.synd.SyndEntry;

public class MessageBuilder {
	
	public static String NS = "http://purl.org/ifgi/ld/usgs#";
	private Random random;
	private StringBuilder sb; 

	public MessageBuilder() {
		random = new Random(); 
		
		
	}
	public  void constructMessage(StringBuilder sb, SyndEntry entry) {
		String namespace = NS;
	
		String[] split = entry.getTitle().split(",");

		String location, magnitude, time, depth; 
		
		magnitude = split[0]; 
		time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(entry.getUpdatedDate()); 
		Object[] fm  = ((ArrayList<Object>) entry.getForeignMarkup()).toArray(); 
		location = "POINT("+((Element) fm[0]).getValue()+")";
		depth = ((Element) fm[1]).getValue(); 
		
		
		// parse message
		
		
		// build O&M documents
		
		sb.append("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.").append('\n'); 
		sb.append("@prefix dc: <http://purl.org/dc/elements/1.1/>.").append('\n'); 
		sb.append("@prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#>.").append('\n');
		sb.append("@prefix gml: <http://purl.org/ifgi/gml/0.2#>.").append('\n'); 
		sb.append("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.").append('\n');
		sb.append("@prefix om: <http://purl.org/ifgi/om#>.").append('\n'); 
		sb.append("@prefix tecl: <http://sweet.jpl.nasa.gov/2.1/realmLandTectonic.owl>.").append('\n');
		sb.append("@prefix ener: <http://sweet.jpl.nasa.gov/2.1/propEnergy.owl>.").append('\n'); 
		sb.append("@prefix space: <http://sweet.jpl.nasa.gov/2.1/propEnergy.owl>.").append('\n'); 
		sb.append("@prefix : <").append(namespace).append(">.").append('\n'); 
		
		
		
		this.constructObservation(sb, time, magnitude, "<http://sweet.jpl.nasa.gov/2.1/realmLandTectonic.owl#SeismicWave>", "<http://sweet.jpl.nasa.gov/2.1/propEnergy.owl#Energy>");
		this.constructObservation(sb, time, depth, "<http://sweet.jpl.nasa.gov/2.1/realmLandTectonic.owl#SeismicWave>", "<http://sweet.jpl.nasa.gov/2.1/propSpaceHeight.owl#Depth>"); 
		this.constructObservation(sb, time, location, "<http://sweet.jpl.nasa.gov/2.1/realmLandTectonic.owl#SeismicWave>", "<http://sweet.jpl.nasa.gov/2.1/propSpace.owl#Location>"); 

	}
	
	private void constructObservation(StringBuilder sb, String time, String value, String foi, String domainProperty) {
		String i = "_"+random.nextInt(9); 
		
		sb.append(":_observation").append(i).append(" a ssn:observation;").append('\n'); 
		sb.append("   ssn:observedProperty :_property").append(i).append(";").append('\n');
		sb.append("   ssn:featureOfInterest ").append(foi).append(";").append('\n');
		sb.append("   ssn:observationResult :_result").append(i).append(".").append('\n');
		sb.append(":_property").append(i).append(" a ssn:Property;").append('\n');
		sb.append("   a ").append(domainProperty).append(".").append('\n');
		sb.append(":_result").append(i).append(" a  ssn:SensorOutput;").append('\n');
		sb.append("   ssn:hasValue _value").append(i).append(".").append('\n');
		sb.append(":_value").append(i).append(" a  ssn:ObservationValue.").append('\n');
		sb.append("   ssn:hasValue \"").append(value).append("\";").append('\n');
		sb.append("   ssn:startTime \"").append(time).append("\".").append('\n');
		
	}


}
