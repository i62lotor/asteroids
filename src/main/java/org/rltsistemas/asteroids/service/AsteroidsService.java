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
package org.rltsistemas.asteroids.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.rltsistemas.asteroids.mapper.AsteroidMapper;
import org.rltsistemas.asteroids.model.Asteroid;
import org.rltsistemas.asteroids.model.Neo;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Responsable de determinar los asteroides recuperados.
 */
@Slf4j
@Service
public class AsteroidsService {

	private NasaNeosService nasaNeosService;

	private AsteroidMapper asteroidMapper;

	public AsteroidsService(NasaNeosService nasaNeosService, AsteroidMapper asteroidMapper) {
		super();
		this.nasaNeosService = nasaNeosService;
		this.asteroidMapper = asteroidMapper;
	}

	/**
	 * Obtiene los asteroides de mayor tamaño medio con riesgo de colisión en un planeta
	 * TODO: mejor idea ordenar y limitar los elementos recuperados (neos) de la
	 * nasa, asi se evita el mapeo de mas Asteroides de la cuenta.
	 * 
	 * @param planetName
	 * @param numberOfAsterois
	 * @return
	 */
	public List<Asteroid> getDangerousAsteroids(String planetName, int numberOfAsterois) {
		return asteroidMapper.map(getMostPotentiallyHazardous(planetName, numberOfAsterois),planetName).stream()
				.sorted(Comparator.comparingDouble(Asteroid::getEstimatedAverageDiameter).reversed())
				.limit(numberOfAsterois).collect(Collectors.toList());
	}

	private List<Neo> getMostPotentiallyHazardous(String planetName, int numberOfAsterois) {
		List<Neo> neos = this.nasaNeosService.locateNearEarthObjects();
		log.info("Se han recuperado " + neos.size() + " NEOs del servicio de la nasa");

		List<Neo> dangerousNeos = neos.stream()
				.filter(neo -> neo.getCloseApproach().stream().anyMatch(ca -> planetName.equals(ca.getOrbitingbody()))
						&& neo.isPotentiallyHazardousAsteroid())				
				.collect(Collectors.toList());
		log.info("- " + dangerousNeos.size() + " Potentially Hazardous");
		return dangerousNeos;

	}

}
