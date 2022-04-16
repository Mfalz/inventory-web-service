package it.mfalzone.inventory.controller.security.authentication.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.mfalzone.inventory.controller.security.IdentityProviderAuthenticationToken;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase.FirebaseAuthenticationProvider;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase.FirebaseIdentityProviderAdapterImpl;
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

	private AuthenticationProvider authenticationProvider;

	public AuthenticationFilter(AuthenticationProvider provider) {
		this.authenticationProvider = provider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(6);
		DecodedJWT decodedJwt = JWT.decode(jwtToken);
		IdentityProviderAuthenticationToken authentication = new IdentityProviderAuthenticationToken(decodedJwt);

		String issuer = decodedJwt.getIssuer();

		if (issuer.startsWith("https://securetoken.google.com")) {
			authenticationProvider = new FirebaseAuthenticationProvider(new FirebaseIdentityProviderAdapterImpl());
		} else if (authenticationProvider == null) {
			throw new IllegalArgumentException("Unknown issuer " + issuer);
		}

		SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(authentication));

		filterChain.doFilter(request, response);
	}

}
