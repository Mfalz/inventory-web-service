package it.mfalzone.inventory.controller;

import it.mfalzone.inventory.controller.security.filters.CustomHttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
public class ControllerIT {

	@Autowired
	private ResourceLoader resourceLoader;

	protected WebTestClient authWebTestClient;

	@Autowired
	protected WebTestClient webTestClient;

	protected Resource loadFileInClasspath(String filename) {
		return resourceLoader.getResource(
				"classpath:" + filename);
	}

	@BeforeEach
	protected void setup() {
		this.authWebTestClient = buildWebTestClient(webTestClient).defaultHeaders(headers -> {
																	  headers.add(CustomHttpHeaders.HEADER_CLIENT_ID, "test-client");
																	  headers.add(CustomHttpHeaders.HEADER_CLIENT_USERNAME, "admin");
																	  headers.add(CustomHttpHeaders.HEADER_CLIENT_PASSWORD, "password");
																	  headers.add(HttpHeaders.AUTHORIZATION, "Bearer token");
																  })
																  .build();
	}

	protected WebTestClient.Builder buildWebTestClient(WebTestClient client) {
		return client.mutate()
					 .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
					 .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}

}
