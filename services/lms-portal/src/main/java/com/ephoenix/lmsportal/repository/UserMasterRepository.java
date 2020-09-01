package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ephoenix.lmsportal.entities.UserMaster;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long>, JpaSpecificationExecutor<UserMaster> {

	UserMaster findByUserLoginIdIgnoreCaseAndIsActive(String userId, Character isActive);

	UserMaster findByUserLoginIdIgnoreCaseOrEmailOrMobileAndIsActive(String userId, String email, String mobile,
			Character isActive);

	UserMaster findByMobileIgnoreCaseAndIsActive(String mobileNum, Character isActive);

	List<UserMaster> findByIdIn(List<Long> userIds);

	List<UserMaster> findByIdInAndIsActive(List<Long> userMstIds, Character isActive);

	UserMaster findByUserLoginIdIgnoreCaseAndIsActiveAndUserTypeId(String userId, Character isActive, Long typeId);

	UserMaster findByIdAndIsActive(Long id, Character isActive);
	
	
	@Query(value = "SELECT u FROM UserMaster as u join UserStudyMap as usm on usm.userId = u.id join StudyPlanEntity as s on s.studyPlanId = usm.studyPlanId  where s.studyPlanName = 'SPOKEN ENGLISH'")
	public Page<UserMaster> findUsersAssociatedWithSpokenEnglish(Pageable pageable);

	UserMaster findByEmailAndIsActive(String email, Character isActive);


}