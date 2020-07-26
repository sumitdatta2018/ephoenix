package com.ephoenix.lmsportal.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.ephoenix.lmsportal.generic.code.ICommonConstants;


public class CustomAuditorAware implements AuditorAware<Long> {

   
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
	 */
	@Override
	public Optional<Long> getCurrentAuditor() {
		//Need to change before deployment to UAT
		Long userId = ICommonConstants.DEFAULT_USER;
		/*if (null != SecurityContextHolder.getContext()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated()) {
				userId = ICommonConstants.DEFAULT_USER;
			} else {
				OAuth2Authentication a = (OAuth2Authentication) authentication;
				@SuppressWarnings("unchecked")
				Map<String, Object> detailMap = (HashMap<String, Object>) a.getUserAuthentication().getDetails();
				String username = (String) detailMap.get("sub");
				
				IMap<String, Long> auditUsers = hzInstance.getMap("auditUsers");
				if(null != auditUsers) {
					userId = auditUsers.getOrDefault(username, ICommonConstants.DEFAULT_USER); 
				}
			}
		}*/
		return Optional.of(userId);
	}

}