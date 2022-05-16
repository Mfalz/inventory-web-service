package it.mfalzone.inventory.controller;

import it.mfalzone.inventory.controller.security.filters.CustomHttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
@ActiveProfiles("integration-tests")
public class ControllerIT {

	@Autowired
	private ResourceLoader resourceLoader;

	protected WebTestClient authWebTestClient;

	@Autowired
	protected WebTestClient webTestClient;

	protected final static String JWT_TOKEN = "eyJhbGciOiJSUzUxMiJ9.eyJpc3MiOiJsb2NhbGhvc3QiLCJzdWIiOiJ1c2VyIiwiYXVkIjoiYXVkIiwiaWF0IjoxNjUwMDIyNDc5LCJleHAiOjE2NTAwMjMwN30.NcXwuNL_F3Hfm1fsCCWKAqw6VK8t6uyJ4obcwdYO0-UMltxAQlgcb2zjiOz6K0riW42KMWfobKJJ-PG2lbwJ9kdH0tRMrd5KXwrjBAV9_9c3GzsFX-hSfYY_uNQswAqgPo9pIqTct4qFVfX6fgc_R2uYSPIv6asQpecupxNfA-RrdV2xQJJezaKArPq4QV8hLKm-M0S-v1jP283bLQUgznXyiGMLGq2YfnI73vlH8AFhxGtCKP5ZZmqQMcWbQ6hbpdqQk_BV_PItZAz9_pjhkjTQYyRuV63DsbGlvazrY9RG3keD7UhZsGJePwWwap3sSyIplMRp4WmVdqCdf9Egkw";

	protected Resource loadFileInClasspath(String filename) {
		return resourceLoader.getResource(
				"classpath:" + filename);
	}

	@BeforeEach
	protected void setup() {
		// TODO: Create JWT token on the fly
		this.authWebTestClient = buildWebTestClient(webTestClient).defaultHeaders(headers -> {
																	  headers.add(CustomHttpHeaders.HEADER_CLIENT_ID, "test-client");
																	  headers.add(CustomHttpHeaders.HEADER_CLIENT_USERNAME, "admin");
																	  headers.add(CustomHttpHeaders.HEADER_CLIENT_PASSWORD, "password");
																	  headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN);
																  })
																  .build();
	}

	protected WebTestClient.Builder buildWebTestClient(WebTestClient client) {
		return client.mutate()
					 .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
					 .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}

}
