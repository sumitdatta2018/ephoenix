package com.ephoenix.lmsportal.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.ephoenix.lmsportal.entities.UserMaster;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.ephoenix.lmsportal.generic.code.ICommonConstants;
import com.ephoenix.lmsportal.repository.UserMasterRepository;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuditorAware implements AuditorAware<Long> {

	@Autowired
	private HazelcastInstance hzInstance;

	@Autowired
	private UserMasterRepository userMasterRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
	 */
	@Override
	public Optional<Long> getCurrentAuditor() {
		// Need to change before deployment to UAT
		Long userId = ICommonConstants.DEFAULT_USER;
		if (null != SecurityContextHolder.getContext()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated()||authentication.getPrincipal().equals("anonymousUser")) {
				userId = ICommonConstants.DEFAULT_USER;
			} else{
				KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) authentication;
				AccessToken token = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
				 String[] split = token.getName().split("|");
				userId = Long.valueOf(token.getName().split("\\|")[1]);

			}
		}
		return Optional.of(userId);
	}

}