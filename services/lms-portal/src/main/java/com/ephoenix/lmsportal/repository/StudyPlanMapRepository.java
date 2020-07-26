package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.UserMaster;
import com.ephoenix.lmsportal.entities.UserStudyMap;

public interface StudyPlanMapRepository extends JpaRepository<UserStudyMap, Long>, JpaSpecificationExecutor<UserStudyMap> {
	
	List<UserStudyMap> findByUserIdAndIsActive(Long userId,Character isActive);

	

}