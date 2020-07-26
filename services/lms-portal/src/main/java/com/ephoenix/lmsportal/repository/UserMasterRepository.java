package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.UserMaster;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long>, JpaSpecificationExecutor<UserMaster> {

	UserMaster findByUserLoginIdIgnoreCaseAndIsActive(String userId, Character isActive);

	UserMaster findByUserLoginIdIgnoreCaseOrEmailOrMobileAndIsActive(String userId, String email, String mobile,
			Character isActive);

	UserMaster findByMobileIgnoreCaseAndIsActive(String mobileNum, Character isActive);

	List<UserMaster> findByIdIn(List<Long> userIds);

	List<UserMaster> findByIdInAndIsActive(List<Long> userMstIds, Character isActive);

	UserMaster findByUserLoginIdIgnoreCaseAndIsActiveAndUserTypeId(String userId, Character isActive, Long typeId);

}