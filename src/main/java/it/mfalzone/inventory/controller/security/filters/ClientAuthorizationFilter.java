package it.mfalzone.inventory.controller.security.filters;

import it.mfalzone.inventory.controller.security.configurations.AllowedClientsConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authenticate and authorize external clients
 */
public class ClientAuthorizationFilter extends OncePerRequestFilter {

	private final AllowedClientsConfiguration allowedClientsConfiguration;

	public ClientAuthorizationFilter(AllowedClientsConfiguration allowedClientsConfiguration) {
		this.allowedClientsConfiguration = allowedClientsConfiguration;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String clientUsername = request.getHeader(CustomHttpHeaders.HEADER_CLIENT_USERNAME);
		String clientPassword = request.getHeader(CustomHttpHeaders.HEADER_CLIENT_PASSWORD);
		String clientId = request.getHeader(CustomHttpHeaders.HEADER_CLIENT_ID);

		if (!allowedClientsConfiguration.isAllowed(clientId, clientUsername, clientPassword)) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		filterChain.doFilter(request, response);
	}
}
