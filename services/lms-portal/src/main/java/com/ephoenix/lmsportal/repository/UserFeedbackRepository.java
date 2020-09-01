package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.RoleMenuMap;
import com.ephoenix.lmsportal.entities.UserClassMap;
import com.ephoenix.lmsportal.entities.UserFeedback;


public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long>, JpaSpecificationExecutor<UserFeedback> {

	UserFeedback findByCreatedByAndIsActive(Long userId, Character isActive);

	List<UserFeedback> findByIsActive(Character isActive);

	List<UserFeedback> findByRatingAndIsActive(Long rating, Character isActive);

}