/*******************************************************************************
 * Copyright 2021 Rafael López Torres
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.rltsistemas.asteroids.mapper;

import java.util.ArrayList;
import java.util.List;

import org.rltsistemas.asteroids.model.Asteroid;
import org.rltsistemas.asteroids.model.CloseApproach;
import org.rltsistemas.asteroids.model.Neo;
import org.springframework.stereotype.Component;

/**
 * Responsable de mapear objetos Neo (near earth object) a objetos Asteroid.
 *
 */
@Component
public class AsteroidMapper {

	
	public List<Asteroid> map(List<Neo> neos, String planetName){
		List<Asteroid> asteroids = new ArrayList<Asteroid>();
		for(Neo neo : neos) {
			asteroids.add(map(neo, planetName));
		}
		return asteroids;
		
	}
	
	public Asteroid map(Neo neo, String planetName){
		Asteroid asteroid = new Asteroid();
		asteroid.setName(neo.getName());
		asteroid.setEstimatedAverageDiameter(neo.getDiameter().getEstimatedAverageDiameter());
		
		CloseApproach ca = neo.getCloseApproach().stream()
				.filter(n -> planetName.equals(n.getOrbitingbody())).findFirst().get();
		asteroid.setPlanet(ca.getOrbitingbody());
		asteroid.setVelocity(ca.getRelativeVelocity());
		asteroid.setCloseApproachDate(ca.getDate());
		return asteroid;
	}
}
