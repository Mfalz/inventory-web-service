package it.mfalzone.inventory.controller.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.IdentityProviderUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class IdentityProviderAuthenticationToken extends AbstractAuthenticationToken {

	private final IdentityProviderUser principalUser;

	private final DecodedJWT decodedJWT;

	public IdentityProviderAuthenticationToken(DecodedJWT decodedJWT) {
		super(Collections.emptyList());
		this.decodedJWT = decodedJWT;
		this.principalUser = null;
		setAuthenticated(false);
	}

	public IdentityProviderAuthenticationToken(DecodedJWT decodedJWT, IdentityProviderUser principalUser) {
		super(null);
		this.decodedJWT = decodedJWT;
		this.principalUser = principalUser;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return decodedJWT;
	}

	@Override
	public Object getPrincipal() {
		return principalUser;
	}
}
