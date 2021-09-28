package org.rltsistemas.asteroids.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NasaNeoWsResponse {

	@JsonProperty("element_count")
	private int elementCount;
	
	@JsonProperty("near_earth_objects")
	private Map<String, List<Neo>> neosDay;
	
	public List<Neo> getAllNeos(){
		return this.neosDay.values().stream()
				.flatMap(List::stream).collect(Collectors.toList());
	}
}
