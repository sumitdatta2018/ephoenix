package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.RoleMaster;

public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long>, JpaSpecificationExecutor<RoleMaster> {

	RoleMaster findByRoleIdAndIsActive(Long roleId, Character isActive);

	RoleMaster findByRoleNameAndIsActive(String authorities, char c);

	List<RoleMaster> findByRoleIdInAndIsActive(List<Long> collect, Character isActive);

	List<RoleMaster> findByIsActive(Character isActive);

	RoleMaster findByRoleNameIgnoreCaseAndIsActive(String userType, Character isActive);

}