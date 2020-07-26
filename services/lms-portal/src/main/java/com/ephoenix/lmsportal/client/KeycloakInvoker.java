package com.ephoenix.lmsportal.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ephoenix.lmsportal.dto.UserTO;

@Service
public class KeycloakInvoker {

	@Autowired
	private Keycloak keycloak;

	public String getToken(Map<String, String> tokenRequest1) throws Exception{
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response =null;
		MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<String, String>();
		// map.add("email", "first.last@example.com");
		tokenRequest.add("client_id", tokenRequest1.get("client_id"));
		tokenRequest.add("client_secret", tokenRequest1.get("client_secret"));
		tokenRequest.add("username", tokenRequest1.get("username"));
		tokenRequest.add("password", tokenRequest1.get("password"));
		tokenRequest.add("grant_type", "password");
		try {
			response = restTemplate.postForEntity(
					"http://ephoenix.org:7003/auth/realms/ephoenix/protocol/openid-connect/token", tokenRequest,
					String.class);
			
		} catch (Exception e) {
			throw e;
		}
		
		return response.getBody();
	}

	public void createKeycloakUser(UserTO userTO) {

		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true);
		user.setUsername(userTO.getUserLoginId());
		user.setFirstName(userTO.getName());
		user.setEmail(userTO.getEmail());
		// user.setAttributes(Collections.singletonMap("origin",
		// Arrays.asList("demo")));

		RealmResource realmResource = keycloak.realm("ephoenix");
		UsersResource userRessource = realmResource.users();

		Response response = userRessource.create(user);
		String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
		resetPassword(userId, userTO.getPassword());

	}

	public void resetPassword(String userId, String password) {
		RealmResource realmResource = keycloak.realm("ephoenix");
		UsersResource userRessource = realmResource.users();

		CredentialRepresentation passwordCred = new CredentialRepresentation();
		passwordCred.setTemporary(false);
		passwordCred.setType(CredentialRepresentation.PASSWORD);
		passwordCred.setValue(password);
		userRessource.get(userId).resetPassword(passwordCred);

	}

	public void revokeToken(String token) {
		TokenManager tokenManager = keycloak.tokenManager();
		tokenManager.invalidate(token);

	}

	public static void main(String[] args) {
		Keycloak keycloak = KeycloakBuilder.builder() //
				.serverUrl("http://ephoenix.org:7003/auth") //
				.realm("ephoenix") //
				.grantType(OAuth2Constants.PASSWORD) //
				.clientId("ephoenix") //
				.clientSecret("7b6b4f28-224e-4045-a6ed-d10014e7ac7f") //
				.username("admin") //
				.password("Welcome@1234") //
				.build();

		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true);
		user.setUsername("tester3");
		user.setFirstName("First");
		user.setLastName("Last");
		user.setEmail("tom+tester3@tdlabs.local");
		user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));

		// Get realm
		RealmResource realmResource = keycloak.realm("ephoenix");
		UsersResource userRessource = realmResource.users();

		// Create user (requires manage-users role)
		Response response = userRessource.create(user);
		String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
		CredentialRepresentation passwordCred = new CredentialRepresentation();
		passwordCred.setTemporary(false);
		passwordCred.setType(CredentialRepresentation.PASSWORD);
		passwordCred.setValue("Welcome@1234");

		// Set password credential
		userRessource.get(userId).resetPassword(passwordCred);

	}

}
