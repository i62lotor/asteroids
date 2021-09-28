package org.rltsistemas.asteroids.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Nasa Earth Object
 * @author lotor
 * TODO: configurar propiedades para que jackson mappe el json automáticamente
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Neo implements Serializable {

	private static final long serialVersionUID = 7311450345220384192L;	
	
	@JsonProperty("id")
	private long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("is_potentially_hazardous_asteroid")
	private boolean potentiallyHazardousAsteroid;
	
	@JsonProperty("estimated_diameter")
	private Diameter diameter;	
	
	@JsonProperty("close_approach_data")
	private List<CloseApproach> closeApproach;
	
	public Neo(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Neo() {
		
	}
	
	
}
