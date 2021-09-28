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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.rltsistemas.asteroids.model.NasaNeoWsResponse;
import org.rltsistemas.asteroids.model.Neo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Responsable de realizar la petición HTTP al WS de la nasa para recuperar los
 * objetos Neo (near earth object).
 */
@Service
public class NasaNeosService {

	
	@Value("${nasa.api.key}")
	private String nasaApiKey;
	
	@Value("${nasa.neo.queryParam.name.start_date}")
	private String startDate;
	
	@Value("${nasa.neo.queryParam.name.end_date}")
	private String endDate;
	
	@Value("${nasa.neo.queryParam.name.api_key}")
	private String apiKey;
	
	@Value("${nasa.neo.days.to.search:7}")
	private int days;
	
	private WebClient webClient;
	
	public NasaNeosService(WebClient webClient) {
		super();
		this.webClient = webClient;
	}


	/**
	 * Nasa please show me Near Earth objects.
	 * 
	 * @return 
	 */
	public List<Neo> locateNearEarthObjects() {
		return httpGetNeosWS().getAllNeos();
	}


	private NasaNeoWsResponse httpGetNeosWS() {
		return webClient.get()
        	.uri(uriBuilder -> uriBuilder
                .queryParam(startDate, formatDate(LocalDate.now()))
                .queryParam(endDate, formatDate(LocalDate.now().plusDays(days)))
                .queryParam(apiKey, nasaApiKey)
                .build())
        	.retrieve()
        	.bodyToMono(NasaNeoWsResponse.class)
        	.block();		
	}	
	
	private String formatDate(LocalDate localdate) {
		return localdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));	
	}

}
