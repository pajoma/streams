package de.ifgi.streams.eventmodel;

import java.util.*;


public class JSONFeature  {
	final String   type = "Feature"; 
	
	private JSONGeometry geometry = null;
	private JSONProperties properties = null;
	//private EventProperties ep = null;
	
	/*private Property time = null;
	private Property source = null;
	private Property v = null;*/
	
	public JSONGeometry getGeometry() {
		return geometry;
	}
	public void setGeometry(JSONGeometry geometry) {
		this.geometry = geometry;
	}  
	
	public JSONProperties getProperties() {
		if (properties == null) {
			properties = new JSONProperties(); 
			
		}
		return properties;
	}
	public void setProperties(JSONProperties properties) {
		this.properties = properties;
	}  
	
/*	public EventProperties getEventProperties() {
		return ep;
	}
	public void setEventProperties(EventProperties eps) {
		this.ep = eps;
	}  */
	
	/*public void setTime(Property time) {
		this.time = time;
	}  
	
	public void setSource(Property source) {
		this.source = source;
	} 
	
	public void setV(Property V) {
		this.v = v;
	}  */
	
	
	

}
