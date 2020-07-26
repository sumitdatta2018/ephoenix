package com.ephoenix.lmsportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

	/**
	 * @return AuditorAware response
	 */
	@Bean
	AuditorAware<Long> auditorProvider() {
		return new CustomAuditorAware();
	}
}