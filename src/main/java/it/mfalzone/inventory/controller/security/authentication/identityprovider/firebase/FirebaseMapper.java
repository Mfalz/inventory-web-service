package it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase;

import com.google.firebase.auth.UserRecord;
import it.mfalzone.inventory.controller.security.authentication.identityprovider.IdentityProviderUser;

public class FirebaseMapper {
	public static IdentityProviderUser map(UserRecord ur) {
		String[] names = splitDisplayName(ur.getDisplayName());
		return new IdentityProviderUser("FIREBASE", ur.getUid(), ur.getEmail(), names[0], names[1]);
	}

	public static String[] splitDisplayName(String displayName) {
		if (displayName != null) {
			return displayName.split(" ");
		}
		return new String[]{"Unknown", "Unknown"};
	}
}
