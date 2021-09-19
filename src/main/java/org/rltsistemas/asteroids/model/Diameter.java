package org.rltsistemas.asteroids.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author lotor
 *
 */
@Data
public class Diameter implements Serializable{


	private static final long serialVersionUID = 1933173052588085470L;
	
	private double estimatedDiameterMin;
	private double estimatedDiameterMax;
	
	public Diameter(double estimatedDiameterMin, double estimatedDiameterMax) {
		super();
		this.estimatedDiameterMin = estimatedDiameterMin;
		this.estimatedDiameterMax = estimatedDiameterMax;
	}
	
	public double getEstimatedAverageDiameter() {
		return (this.estimatedDiameterMax+this.estimatedDiameterMin)/2;
	}


	
}
