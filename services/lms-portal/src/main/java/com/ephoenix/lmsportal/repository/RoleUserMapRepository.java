package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.RoleUserMap;


public interface RoleUserMapRepository extends JpaRepository<RoleUserMap, Long>, JpaSpecificationExecutor<RoleUserMap> {

	List<RoleUserMap> findByUserIdAndIsActive(Long userId, Character isActive);

	RoleUserMap findByRoleIdAndUserIdAndIsActive(Long roleId, Long userId, Character isActive);

	List<RoleUserMap> findByRoleIdInAndIsActive(List<Long> roleIds, Character isActive);    
	
	List<RoleUserMap> findByUserIdInAndIsActive(List<Long> userId, Character isActive);

	List<RoleUserMap> findByRoleIdAndIsActive(Long roleId, Character isActive); 

}