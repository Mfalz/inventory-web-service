package it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase;

import com.auth0.jwt.interfaces.DecodedJWT;
import it.mfalzone.inventory.controller.security.IdentityProviderAuthenticationToken;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.IdentityProviderUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Profile("!integration-tests")
@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

	private final FirebaseIdentityProviderAdapterImpl firebaseIdentityProviderAdapter;

	public FirebaseAuthenticationProvider(FirebaseConfiguration configuration) {
		firebaseIdentityProviderAdapter = new FirebaseIdentityProviderAdapterImpl(configuration.getFirebaseInstance());
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		DecodedJWT jwt = (DecodedJWT) authentication.getCredentials();

		String email = jwt.getClaim("email").asString();
		IdentityProviderUser user = firebaseIdentityProviderAdapter.getUserByEmail(email);

		return new IdentityProviderAuthenticationToken(jwt, user);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}
}
