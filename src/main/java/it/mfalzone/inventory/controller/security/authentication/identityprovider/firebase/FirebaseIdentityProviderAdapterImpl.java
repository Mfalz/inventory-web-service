package it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.IdentityProviderAdapter;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.IdentityProviderUser;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!integration-tests")
@Component
public class FirebaseIdentityProviderAdapterImpl implements IdentityProviderAdapter {

	private final FirebaseAuth firebaseAuth;

	public FirebaseIdentityProviderAdapterImpl(FirebaseAuth firebaseAuth) {
		this.firebaseAuth = firebaseAuth;
	}

	@Override
	public IdentityProviderUser getUserByEmail(String email) {
		try {
			return FirebaseMapper.map(firebaseAuth.getUserByEmail(email));
		} catch (FirebaseAuthException e) {
			throw new RuntimeException(e);
		}
	}
}
