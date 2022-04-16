package it.mfalzone.inventory.controller;

import it.mfalzone.inventory.controller.security.authentication.identityprovider.IdentityProviderUser;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase.FirebaseIdentityProviderAdapterImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FirebaseAdapterIT extends ControllerIT {

	@Autowired
	FirebaseIdentityProviderAdapterImpl firebaseAdapter;

	@Test
	void firebaseAppIsInitialized() {
		IdentityProviderUser user = firebaseAdapter.getUserByEmail("michelefalzone@gmail.com");
		assertNotNull(user);
		assertThat(user.getEmail()).isEqualTo("michelefalzone@gmail.com");
		assertThat(user.getExternalId()).startsWith("3Tgs13ez");
		assertThat(user.getFirstName()).isEqualTo("Unknown");
		assertThat(user.getLastName()).isEqualTo("Unknown");
	}
}
