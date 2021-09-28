package org.rltsistemas.asteroids.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author lotor
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Diameter implements Serializable{


	private static final long serialVersionUID = 1933173052588085470L;
	
	private double estimatedDiameterMin;
	private double estimatedDiameterMax;
	
	public Diameter(double estimatedDiameterMin, double estimatedDiameterMax) {
		super();
		this.estimatedDiameterMin = estimatedDiameterMin;
		this.estimatedDiameterMax = estimatedDiameterMax;
	}
	
	public Diameter() {
		
	}
	
	public double getEstimatedAverageDiameter() {
		return (this.estimatedDiameterMax+this.estimatedDiameterMin)/2;
	}
	
	@JsonProperty("kilometers")
	private void mapNested(Map<String, String> km) {
		this.estimatedDiameterMin = Double.valueOf(km.get("estimated_diameter_min"));
		this.estimatedDiameterMax = Double.valueOf(km.get("estimated_diameter_max"));
	}

	
	
}
