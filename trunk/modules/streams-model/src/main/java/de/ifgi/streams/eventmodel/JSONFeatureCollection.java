package de.ifgi.streams.eventmodel;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

public class JSONFeatureCollection  {
	
	private String type ="FeatureCollection"; 
	 /*
	  { "type": "FeatureCollection",
		  "features": [
		    { "type": "Feature",
		      "geometry": {"type": "Point", "coordinates": [102.0, 0.5]},
		      "properties": {"prop0": "value0"}
		    }

		]
		}
		
		
		{ "features" : [ 
			{ 
			"geometry" : { "coordinates" : { "coordinates" : [ 102.0,0.5] }, "type" : "Point"},
       		"type" : "Feature"
      		} ],
  "type" : "FeatureCollection"
}
	  * 
	  */
	
	
	public JSONFeatureCollection() {
		
	}

	private List<JSONFeature> features; 
	
	public List<JSONFeature> getFeatures() {
		if (features == null) {
			features = new ArrayList<JSONFeature>();
			
		}

		return features;
	}
	

	public void setFeatures(List<JSONFeature> features) {
		this.features = features;
	}

	public void addFeature(JSONFeature f) {
		getFeatures().add(f); 
		
	}
	
	public int featureNumber(){
		return this.features.size();
	}
	
	public void clear(){
		this.features.clear();
	}
	
	
}
