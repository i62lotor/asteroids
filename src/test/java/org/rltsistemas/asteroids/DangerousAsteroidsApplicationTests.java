package org.rltsistemas.asteroids;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DangerousAsteroidsApplicationTests {

	@LocalServerPort
	private int port;
	
	WebTestClient client;

	@BeforeEach
	void setUp(ApplicationContext context) {  
		client = WebTestClient.bindToServer()
				.baseUrl("http://localhost:"+port)				
                .responseTimeout(Duration.ofMillis(30000))
				.build();
	}

	@Test
	public void shouldReturnOkStatus() throws Exception {

		client.get().uri(uriBuilder -> uriBuilder.pathSegment("asteroids")
				.queryParam("planet", "Earth")
				.build()).exchange().expectStatus()
				.isOk();

	}
}
