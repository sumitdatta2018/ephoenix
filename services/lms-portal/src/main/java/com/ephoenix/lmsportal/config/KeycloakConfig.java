package com.ephoenix.lmsportal.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig  {

	@Bean
	public Keycloak getKeycloak() {

		Keycloak keycloak = KeycloakBuilder.builder() //
				.serverUrl("http://ephoenix.org:7003/auth") //
				.realm("ephoenix") //
				.grantType(OAuth2Constants.PASSWORD) //
				.clientId("ephoenix") //
				.clientSecret("7b6b4f28-224e-4045-a6ed-d10014e7ac7f") //
				.username("admin") //
				.password("Welcome@1234") //
				.build();
		return keycloak;

	}
}