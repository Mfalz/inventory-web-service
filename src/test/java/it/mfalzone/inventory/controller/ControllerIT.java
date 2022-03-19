package it.mfalzone.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
public class ControllerIT {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	protected WebTestClient webTestClient;

	public Resource loadFileInClasspath(String filename) {
		return resourceLoader.getResource(
				"classpath:" + filename);
	}
}
