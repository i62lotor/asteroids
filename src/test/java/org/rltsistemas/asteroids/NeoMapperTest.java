package org.rltsistemas.asteroids;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.rltsistemas.asteroids.mapper.NeoMapper;
import org.rltsistemas.asteroids.model.Neo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class NeoMapperTest {

	private static long NEO_ID = 54197674;
	private static String NEO_NAME = "(2021 RJ19)";
	
	@Value("classpath:near_earth_object.json")
	Resource neoFile;
	
	@Value("classpath:nasa_neo_ws_response.json")
	Resource nasaNeoWSResponseFile;
	
	@Autowired
	private NeoMapper responseMapper;
	
	@Test
	void givenNeoJsonWhenBuildNeoThenGetNeoObject() {
		try {
			
			ObjectMapper mapper  = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(neoFile.getInputStream()); 
			Neo neo = responseMapper.buildNeo(jsonNode);
			assertTrue(neo.getId() == NEO_ID);
			assertTrue(NEO_NAME.equals(neo.getName()));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void givenNeoWSJsonResponseWhenMapThenGetNeoObjectList() {
		try {
			
			String response = new String(nasaNeoWSResponseFile.getInputStream().readAllBytes(), "UTF-8"); 
			List<Neo> neos = responseMapper.map(response);
			
			assertTrue(neos.size() == 19);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
}
