package it.mfalzone.inventory.controller.configuration;

import com.auth0.jwt.interfaces.DecodedJWT;
import it.mfalzone.inventory.controller.security.IdentityProviderAuthenticationToken;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.IdentityProviderUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class DummyAuthenticationProvider implements AuthenticationProvider {

	public final static String DUMMY_USER_EMAIL = "dummy@email.com";

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		DecodedJWT jwt = (DecodedJWT) authentication.getPrincipal();
		IdentityProviderUser identityProviderUser = new IdentityProviderUser("DUMMY",
																			 "id-123",
																			 DUMMY_USER_EMAIL,
																			 "firstname",
																			 "lastname");
		return new IdentityProviderAuthenticationToken(jwt, identityProviderUser);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}
}
