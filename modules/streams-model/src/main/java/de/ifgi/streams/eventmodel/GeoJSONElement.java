package de.ifgi.streams.eventmodel;

import com.google.gson.JsonElement;

public class GeoJSONElement extends JsonElement {

	private final String type;

	public GeoJSONElement(String type) {
		this.type = type;
		
	}
	
	public String getType()  {
		return type; 
	}
}
