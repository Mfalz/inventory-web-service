package it.mfalzone.inventory.controller.security.authentication.identityprovider;

public interface IdentityProviderAdapter {
	IdentityProviderUser getUserByEmail(String email);
}
