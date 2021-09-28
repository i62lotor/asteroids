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
package org.rltsistemas.asteroids;

import java.util.ArrayList;
import java.util.List;

import org.rltsistemas.asteroids.model.CloseApproach;
import org.rltsistemas.asteroids.model.Diameter;
import org.rltsistemas.asteroids.model.NasaNeoWsResponse;
import org.rltsistemas.asteroids.model.Neo;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Responsable de mapear la respuesta del servicio de la Nasa en Json
 * a objetos Neo.
 * 
 * TODO: sería mas interesante que el mapeo lo hiciese jackson automáticamente (configurar las clases del modelo). 
 */
@Slf4j
@Component
public class NeoMapper {

	public List<Neo> map(String neoServiceJsonResponse){
		List<Neo> neos = new ArrayList<Neo>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNodeRoot = mapper.readTree(neoServiceJsonResponse);
			JsonNode jsonNodeDays = jsonNodeRoot.get("near_earth_objects");
			 
			log.info("Días recuperados:"+jsonNodeDays.size());
			
			for(JsonNode node: jsonNodeDays) {
				if(node.isArray()) {					
					log.info("Numero de Neos: "+node.size());
					node.forEach(n -> {						
						neos.add(buildNeo(n));
					});
				}
			}
			
		} catch (JsonProcessingException  e) {
			log.error("ERROR al mapear NEO"+e);
			e.printStackTrace();
		}
		return neos;
	}

	public Neo buildNeo(JsonNode neo) {
		Neo neoObj = new Neo(neo.findValue("id").asLong(),
				neo.findValue("name").asText());
							
		neoObj.setPotentiallyHazardousAsteroid(neo.findValue("is_potentially_hazardous_asteroid").asBoolean());
		
		neoObj.setDiameter(buildDiameterKilometer(neo));
		neoObj.setCloseApproach(getCloseApproachElement(neo));
		return neoObj;
	}

	private Diameter buildDiameterKilometer(JsonNode neo) {
		JsonNode esimatedDiameterKm = neo.get("estimated_diameter")
				.get("kilometers");
		return new Diameter(esimatedDiameterKm.findValue("estimated_diameter_min").asDouble(),
				esimatedDiameterKm.findValue("estimated_diameter_max").asDouble());
	}
	
	private List<CloseApproach> getCloseApproachElement(JsonNode neo) {
		JsonNode closeApproachData =  neo.get("close_approach_data");
		List<CloseApproach> closeApproachElements = new ArrayList<CloseApproach>();
		if(closeApproachData.isArray()) {
			closeApproachData.forEach(ca -> closeApproachElements.add(buildCloseApproach(ca)) );
		}
		return closeApproachElements;
		
	}
	
	private CloseApproach buildCloseApproach(JsonNode closeApproachNode) {
		
		CloseApproach closeApproach = new CloseApproach();
		closeApproach.setOrbitingbody(closeApproachNode.findValue("orbiting_body").asText());
		closeApproach.setRelativeVelocity(closeApproachNode.get("relative_velocity").findValue("kilometers_per_hour").asDouble());
		closeApproach.setDate(closeApproachNode.findValue("close_approach_date").asText());
		
		
		return closeApproach;
	}
	
	public List<Neo> autoMap(String neoServiceJsonResponse) {
		ObjectMapper mapper = new ObjectMapper();
		NasaNeoWsResponse respobj = new NasaNeoWsResponse();
		List<Neo> neos = new ArrayList<>();
		try {
			respobj = mapper.readValue(neoServiceJsonResponse, NasaNeoWsResponse.class);
			respobj.getNeosDay().values().stream().forEach(n -> neos.addAll(n));
		} catch (JsonProcessingException e) {			
			log.error("ERROR al mapear json de respuesta"+e);
		}		
		
		return neos;
	}
}
