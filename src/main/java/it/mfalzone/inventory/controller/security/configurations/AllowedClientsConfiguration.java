package it.mfalzone.inventory.controller.security.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "application")
@Data
public class AllowedClientsConfiguration {
	private Map<String, ClientConfiguration> allowedClients;

	public boolean isAllowed(String clientId, String username, String password) {
		if (allowedClients.containsKey(clientId)) {
			var clientConfiguration = allowedClients.get(clientId);
			return clientConfiguration.getClientUsername().equals(username) && clientConfiguration.getClientPassword().equals(password);
		}
		return false;
	}

	@Data
	static class ClientConfiguration {
		private String clientUsername;
		private String clientPassword;
	}
}
