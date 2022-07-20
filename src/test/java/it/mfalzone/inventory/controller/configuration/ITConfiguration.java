package it.mfalzone.inventory.controller.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;

@TestConfiguration
public class ITConfiguration {

	@Bean
	@Primary
	AuthenticationProvider dummyAuthenticationProvider() {
		return new DummyAuthenticationProvider();
	}

}
