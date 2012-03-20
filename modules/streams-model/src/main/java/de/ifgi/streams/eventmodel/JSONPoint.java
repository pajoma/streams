package de.ifgi.streams.eventmodel;


public class JSONPoint extends JSONGeometry{
	
	final String type = "Point"; 
	private float[] coordinates; 
	
	

	public float[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(float[] coordinate) {
		this.coordinates = coordinate;
	}
	
	
	


}
