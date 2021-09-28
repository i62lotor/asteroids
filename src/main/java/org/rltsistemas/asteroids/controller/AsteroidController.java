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
package org.rltsistemas.asteroids.controller;

import java.util.List;
import java.util.Optional;

import org.rltsistemas.asteroids.model.Asteroid;
import org.rltsistemas.asteroids.service.AsteroidsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AsteroidController {

	@Autowired
	private AsteroidsService asteroidsService;
	
	/**
	 * Obtiene el número (por defecto 3) de asteroides de mayor tamaño con riesgo de colisión con el planeta indicado
	 * @param planetName: nombre del planeta
	 * @param maxNumber: número máximo de asteroides a mostrar
	 * @return
	 */
	@GetMapping(value = "/asteroids", produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<List<Asteroid>> getMostDangerousAsteroids(
			@RequestParam(name = "planet", required = true) String planetName,
			@RequestParam(name = "max", required = false, defaultValue = "3") int maxNumber) {

		try {
			List<Asteroid> asteroids = asteroidsService.getDangerousAsteroids(planetName, maxNumber);
			
			return ResponseEntity.of(Optional.ofNullable(asteroids));
		}catch (Exception e) {
			log.error("ERROR no esperado: "+e);
			throw new ResponseStatusException(
			           HttpStatus.I_AM_A_TEAPOT, "ups!!!", e);
		}
	}
}
