package org.rltsistemas.asteroids.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Asteroid implements Serializable {

	private static final long serialVersionUID = -2325086442345513207L;
	
	private String name;
	private double estimatedAverageDiameter;
	private double velocity;
	private String closeApproachDate;
	private String planet;


}
