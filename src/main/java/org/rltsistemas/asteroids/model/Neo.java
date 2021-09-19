package org.rltsistemas.asteroids.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Nasa Earth Object
 * @author lotor
 * TODO: configurar propiedades para que jackson mappe el json automáticamente
 */
@Data
public class Neo implements Serializable {

	private static final long serialVersionUID = 7311450345220384192L;	
	
	private long id;
	
	private String name;
	
	@JsonProperty("is_potentially_hazardous_asteroid")
	private boolean potentiallyHazardousAsteroid;
	
	private Diameter diameter;	
	
	private List<CloseApproach> closeApproach;
	
	public Neo(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
}
