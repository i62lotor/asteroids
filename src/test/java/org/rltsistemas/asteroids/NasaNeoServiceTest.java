package org.rltsistemas.asteroids;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.rltsistemas.asteroids.model.Neo;
import org.rltsistemas.asteroids.service.NasaNeosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NasaNeoServiceTest {

	@Autowired
	private NasaNeosService neoService;
	
	
	@Test
	void whenSearch4NeosThenRetrieveNeosList() {
		List<Neo> neos = neoService.locateNearEarthObjects();
		
		assertFalse(neos.isEmpty());
	}

}
