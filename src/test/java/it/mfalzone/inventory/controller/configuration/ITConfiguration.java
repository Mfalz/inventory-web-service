package it.mfalzone.inventory.controller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
public class ITConfiguration {

	@Bean
	@Primary
	AuthenticationProvider dummyAuthenticationProvider() {
		return new DummyAuthenticationProvider();
	}
}
