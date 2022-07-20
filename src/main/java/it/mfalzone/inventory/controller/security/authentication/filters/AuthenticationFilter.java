package it.mfalzone.inventory.controller.security.authentication.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.mfalzone.inventory.controller.security.IdentityProviderAuthenticationToken;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase.FirebaseAuthenticationProvider;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase.FirebaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(6);
		DecodedJWT decodedJwt = JWT.decode(jwtToken);
		IdentityProviderAuthenticationToken authentication = new IdentityProviderAuthenticationToken(decodedJwt);

		SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(authentication));

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURI().equals("/actuator/health");
	}

}
