package it.mfalzone.inventory.controller.security.authentication.identityprovider.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Profile("!integration-tests")
@Configuration
public class FirebaseConfiguration {

	protected Logger LOG = LogManager.getLogger(FirebaseConfiguration.class);

	@Value("${firebase.service-account}")
	private String firebaseServiceAccountFilename;

	public void setFirebaseServiceAccountFilename(String firebaseServiceAccountFilename) {
		this.firebaseServiceAccountFilename = firebaseServiceAccountFilename;
	}

	public void setFirebaseProjectId(String firebaseProjectId) {
		this.firebaseProjectId = firebaseProjectId;
	}

	@Value("${firebase.project-id}")
	private String firebaseProjectId;

	public String getFirebaseServiceAccountFilename() {
		return firebaseServiceAccountFilename;
	}

	public String getFirebaseProjectId() {
		return firebaseProjectId;
	}

	@Bean
	public FirebaseAuth getFirebaseInstance() {
		FirebaseOptions options = null;
		try {
			Resource serviceAccountFile = new ClassPathResource(this.getFirebaseServiceAccountFilename());
			options = FirebaseOptions.builder()
									 .setCredentials(GoogleCredentials.fromStream(serviceAccountFile.getInputStream()))
									 .setProjectId(this.getFirebaseProjectId())
									 .build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		FirebaseApp.initializeApp(options);
		return FirebaseAuth.getInstance();
	}

}
