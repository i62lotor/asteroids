package org.rltsistemas.asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rltsistemas.asteroids.mapper.AsteroidMapper;
import org.rltsistemas.asteroids.model.Asteroid;
import org.rltsistemas.asteroids.model.Neo;
import org.rltsistemas.asteroids.service.AsteroidsService;
import org.rltsistemas.asteroids.service.NasaNeosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
public class AsteroidsServiceMockTest {

	@Value("classpath:nasa_neo_ws_response.json")
	Resource nasaNeoWSResponseFile;
	
	private static int DANGEROUS_ASTEROIDS_ESPECTED= 1;
	
	private static String DANGEROUS_ASTEROIDS_ESPECTED_NAME = "(2005 QN11)";
	
	private static String PLANET = "Earth";
	@Mock
    private NasaNeosService neosService;
	
	@Autowired
	private NeoMapper neoMapper;
	
	@Autowired
	private AsteroidMapper asteroidMapper;

	@InjectMocks 
    private AsteroidsService asteroidsService;
    
    @BeforeEach
    void setMockOutput() {
    	String response;
		try {
			asteroidsService = new AsteroidsService(neosService, asteroidMapper);
			response = new String(nasaNeoWSResponseFile.getInputStream().readAllBytes(), "UTF-8");
			List<Neo> neos = neoMapper.autoMap(response);
	        when(neosService.locateNearEarthObjects()).thenReturn(neos);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    @DisplayName("Test Mock AsteroidService")
    @Test
    void givenAplanetAnd3MaxAsteroidsWhenCall4DangerousAsteroidsThenGet1Asteroid() {    
    	List<Asteroid> asteroids = asteroidsService.getDangerousAsteroids(PLANET, 3);
        assertEquals(DANGEROUS_ASTEROIDS_ESPECTED, asteroids.size());
        assertEquals(DANGEROUS_ASTEROIDS_ESPECTED_NAME, asteroids.get(0).getName());
    }
	
}
