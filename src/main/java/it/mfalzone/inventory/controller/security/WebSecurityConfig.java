package it.mfalzone.inventory.controller.security;

import it.mfalzone.inventory.controller.configurations.AllowedClientsConfiguration;
import it.mfalzone.inventory.controller.security.filters.ClientAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AllowedClientsConfiguration allowedClientsConfiguration;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf().disable()
				.addFilterAfter(new ClientAuthorizationFilter(allowedClientsConfiguration), BasicAuthenticationFilter.class);
	}

}