package de.ifgi.streams.eventmodel;

import com.google.gson.JsonElement;

public class JSONCoordinate extends JsonElement {
	private float[] coordinates;

	public JSONCoordinate(float x, float y) {
		coordinates = new float[] {x, y}; 
	}

	
	public float[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
	} 
	
	
	
	
}
