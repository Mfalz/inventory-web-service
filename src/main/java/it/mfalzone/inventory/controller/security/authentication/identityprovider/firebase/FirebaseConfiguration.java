package it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
class FirebaseConfiguration {
	@Value("${firebase.service-account}")
	private String firebaseServiceAccountFilename;

	@Value("${firebase.project-id}")
	private String firebaseProjectId;

	@Bean
	FirebaseAuth initializeFirebase() {
		FirebaseOptions options = null;
		try {
			Resource serviceAccountFile = new ClassPathResource(firebaseServiceAccountFilename);
			options = FirebaseOptions.builder()
									 .setCredentials(GoogleCredentials.fromStream(serviceAccountFile.getInputStream()))
									 .setProjectId(firebaseProjectId)
									 .build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		FirebaseApp.initializeApp(options);
		return FirebaseAuth.getInstance();
	}

}
