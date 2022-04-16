package it.mfalzone.inventory.controller.security.authentication.identityprovider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IdentityProviderUser {
	private final String identityProviderId;
	private final String externalId;
	private final String email;
	private final String firstName;
	private final String lastName;
}
